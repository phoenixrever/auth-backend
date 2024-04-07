package com.phoenixhell.authbackend.service.impl;

import com.phoenixhell.authbackend.entity.LoginUserDetails;
import com.phoenixhell.authbackend.entity.vo.LoginVo;
import com.phoenixhell.authbackend.entity.vo.SignVo;
import com.phoenixhell.authbackend.entity.vo.UserRoleMenuVo;
import com.phoenixhell.authbackend.service.LoginService;
import com.phoenixhell.authbackend.service.UserService;
import com.phoenixhell.authbackend.utils.ExceptionCode;
import com.phoenixhell.authbackend.utils.JacksonUtil;
import com.phoenixhell.authbackend.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *  自定义login方法
 *
 *  认证成功后， AuthenticationManager 身份管理器返回一个被填充满了信息的
 *  （包括上面提到的权限信息，身份信息，细节信息，但密码通常会被移除） Authentication 实例。
 *   Authentication  通常情况下是UsernamePasswordAuthenticationToken这个实现类
 *
 *  Authentication 实例:
 *     getAuthorities()，权限信息列表
 *     getCredentials()，凭证信息，用户输入的密码字符串，在认证过后通常会被移除
 *     getDetails()，细节信息，web应用中的实现接口通常为 WebAuthenticationDetails，它记录了访问者的ip地址和sessionId的值。
 *     getPrincipal()，身份信息，大部分情况下返回的是UserDetails接口的实现类
 *
 *  把登陆的用户名密码 封装成 UsernamePasswordAuthenticationToken
 *  调用 AuthenticationManager 实现类的 authenticate(Authentication) 方法
 *  自动和userDetailService查出来的用户名密码进行校验
 *
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final String TOKEN_PREFIX="token:";
    //过期时间24小时
    private static final Long expireTime=1000 * 60 * 60 * 24 * 30L ;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    //@Autowired
    //private UsersRolesService usersRolesService;


    @Override
    public String login(LoginVo loginVo) {
        //todo 验证码验证成功 删除验证码

        //安装 alt + ctrl 单击显示 实现类
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginVo.getUsername(), loginVo.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);

        if(Objects.isNull(authentication)){
            //在这里抛出的异常 SpringSecurity 会被 ExceptionTranslationFilter 自动捕获并转换UsernameNotFoundException为BadCredentialsException
            //然后再全局捕获 自定义错误 这边写的错误message 没啥用 最后BadCredentialsException有自己的错误信息 还是国际化的
            throw new UsernameNotFoundException(ExceptionCode.LOGIN_EXCEPTION.getMessage());
        }

        //密码比对正确继续执行 LoginUserDetails里面的 密码注意清除
        //getPrincipal()，身份信息，大部分情况下返回的是UserDetails接口的实现类
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();

        //todo 从数据库中获取用户的所有permissions

        String username = userDetails.getUsername();

        //userDetails 存入redis ，用户请求request 带着token 访问的时候 jwttokenFilter的时候可以根据token username 获取对应菜单和权限
        //expireTime token 的过期时间 如果redis 里面没有这个token 说明过期了 不用解析token自带的时间，如果不用redis 这边可以解析token自身的时间

        //多做一步吧清除 密码 最后是复制一个改
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        BeanUtils.copyProperties(userDetails,loginUserDetails );
        loginUserDetails.removePassword();
        String  userDetailsJson = JacksonUtil.toJsonNoException(loginUserDetails);

        if(userDetailsJson!=null){
            stringRedisTemplate.opsForValue().set(TOKEN_PREFIX+username,userDetailsJson,expireTime, TimeUnit.MILLISECONDS);
        }

        //生成jwt
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username",username);

        String token = JwtUtil.generate(claims, expireTime, userDetails.getUsername());
//        System.out.println(token);
//        System.out.println(JwtUtil.getClaim(token));

        return  token;
    }



    @Override
    public void logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        // 如果已经登陆
        if (authentication != null) {
            //这里强转的前提是 UsernamePasswordAuthenticationToken(loginUserDetails, null,authorities); 传递的是loginUserDetails 而不是username 字符串
            LoginUserDetails loginUserDetails = (LoginUserDetails)authentication.getPrincipal();
            SecurityContextHolder.clearContext();
            stringRedisTemplate.delete(TOKEN_PREFIX+loginUserDetails.getUsername());
        }
    }

    @Override
    public Boolean sign(SignVo signVo) {
        return null;
    }
}
