package com.phoenixhell.authbackend.filter;

import com.phoenixhell.authbackend.entity.LoginUserDetails;
import com.phoenixhell.authbackend.exception.MyException;
import com.phoenixhell.authbackend.utils.ExceptionCode;
import com.phoenixhell.authbackend.utils.JacksonUtil;
import com.phoenixhell.authbackend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 注意:
 *      @ControllerAdvice和@ExceptionHanlder组合拦截，不能拦截Filter中的异常
 *      @ControllerAdvice只是对Controller做了加强，而Filter在Controller之前进行
 *
 *      Filter 是在请求进入 Servlet 容器时执行的过滤器，它位于请求处理的早期阶段，在 Spring MVC 的控制器之前执行。
 *      而 @RestControllerAdvice 主要是用于捕获 Spring MVC 中控制器层的异常，因此无法直接捕获 Filter 中的异常。
 *
 *      OncePerRequestFilter 是 Spring Security 提供的一个抽象类，用于创建自定义的过滤器，确保每个请求只会被过滤一次。
 *
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String AUTHENTICATION_PREFIX = "Bearer ";
    private static final String TOKEN_PREFIX="token:";
    @Resource
    private StringRedisTemplate stringredisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 如果已经通过认证
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            /**
             *   chain.doFilter调用下一个filter,最后一个filter方法完成后会回到
             *   最初调用的这里 执行剩余的方法 和递归差不多
             *   这里return 不要执行下面的方法了
             */
            return;
        }

        // 获取 header token 没有就直接进入下一个过滤器（放行）  因为获取不到认证 后面的filter 会抛异常
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith(AUTHENTICATION_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        //如果请求头中有token 并且不过期,则进行解析获取username (后端redis也会设置过期时间 保险一点总没错)
        final String token = header.replace(AUTHENTICATION_PREFIX,"").trim();
        Claims claim = JwtUtil.getClaim(token);
        if (claim==null || JwtUtil.isExpired(token)) {
            chain.doFilter(request, response);
            return;
        }

        //如果获取到用户信息说明 token验证正常并且不过期
        String username = claim.getSubject();

        //获取userDetail 如果连获取不到说明此token已经被删除了(没有过期，过期的在上一步 token 就isExpired 了)
        String userDetailJson = stringredisTemplate.opsForValue().get(TOKEN_PREFIX + username);
        LoginUserDetails loginUserDetails = JacksonUtil.toObjectNoException(userDetailJson, LoginUserDetails.class);

        if(loginUserDetails==null){
            chain.doFilter(request, response);
            return;
        }

        //getAuthorities 会拿存入redis里面的permissions 自动生成
        Collection<? extends GrantedAuthority> authorities = loginUserDetails.getAuthorities();

        //3个参数的构造函数 会设置认证状态 super.setAuthenticated(true);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,authorities);

        //将当前请求的详细信息（例如客户端的IP地址、会话ID等）添加到认证对象中。ip地址会用到
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //authentication 放入security 上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
