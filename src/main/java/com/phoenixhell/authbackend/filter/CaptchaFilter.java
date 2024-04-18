package com.phoenixhell.authbackend.filter;


import com.alibaba.fastjson.JSON;
import com.phoenixhell.authbackend.exception.MyException;
import com.phoenixhell.authbackend.utils.ExceptionCode;
import com.phoenixhell.authbackend.utils.R;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *  OncePerRequestFilter 能够确保在一次请求中只通过一次filter
 * filter 必须加 @Component 注入到容器
 *
 * 这里用法filter和 interceptor 没有区别 只是filter 在请求处理之前执行
 *
 *    Filter前处理 --> Interceptor前处理 --> controller--> Interceptor后处理 --> Filter后处理
 *
 * 注意  ，InputStream只能被读取一次，后面就读取不到了。因此我们在过滤器的时候，已经将InputStream读取过了一次，
 * 当来到Controller，SpringBoot读取InputStream的时候自然是什么都读取不到了
 *
 * 具体见  文档 filter获取POST请求的JSON参数
 *
 * 可以  继承 HttpServletRequestWrapper  再在doFilter方法中放入此类
 *
 * 不过这样所有 inputStream 都会读取2次 ，如果数据非常大  将会很消耗性能
 *
 *  解决方案：
 *         1 就读2次
 *         2 不在filter 里面验证验证码 而改在token 登录时候验证(选择此方案)
 *
 */

@Component
public class CaptchaFilter extends OncePerRequestFilter {
    private static final String CAPTCHA_PREFIX="captcha:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("----------------------");
        String remoteAddr = request.getRemoteAddr();
        System.out.println(remoteAddr);
        //todo 一转化request 就错误
            //拦截 login,register 需要验证吗的url
            if(request.getRequestURI().contains("login") ||request.getRequestURI().contains("signUp")){
                String code =request.getParameter("code");
                String captchaKey= request.getParameter("captchaKey");
                String redisCaptcha = stringRedisTemplate.opsForValue().get(CAPTCHA_PREFIX + captchaKey);
                if(StringUtils.hasText(captchaKey) && StringUtils.hasText(code) && code.equalsIgnoreCase(redisCaptcha)){
                    //todo 验证码验证成功 删除验证码
                    filterChain.doFilter(request,response);
                }else{
                    //就方法 直接写入response返回
                    ////filter里面定义的异常 security是捕获不了的 响应返回自定义数据只能在这之间响应response返回
                    //R error = R.error(ExceptionCode.CAPTCHA_EXCEPTION.getCode(),ExceptionCode.CAPTCHA_EXCEPTION.getMessage());
                    ////還有验证码错了 返回状态吗也要是200 不然前端response 会算作异常直接放到catch error 里面
                    //response.setStatus(HttpServletResponse.SC_OK);
                    //response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    //response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                    //response.getWriter().write(JSON.toJSONString(error));

                    //新方法直接返回到 controller层全局错误捕捉
                    MyException myException = new MyException(ExceptionCode.CAPTCHA_EXCEPTION.getCode(),ExceptionCode.CAPTCHA_EXCEPTION.getMessage());
                    handlerExceptionResolver.resolveException(request, response, null, myException);

                }
            }else{
                //不是登陆或者注册没有验证码 直接放行
                filterChain.doFilter(request,response);
            }
    }
}
