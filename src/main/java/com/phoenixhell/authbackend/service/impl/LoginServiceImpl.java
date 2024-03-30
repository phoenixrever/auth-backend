package com.phoenixhell.authbackend.service.impl;

import com.phoenixhell.authbackend.entity.LoginUserDetails;
import com.phoenixhell.authbackend.entity.vo.LoginVo;
import com.phoenixhell.authbackend.entity.vo.SignVo;
import com.phoenixhell.authbackend.service.LoginService;
import com.phoenixhell.authbackend.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

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


    @Override
    public String login(LoginVo loginVo) {
        //todo 验证码验证成功 删除验证码

        //安装 alt + ctrl 单击显示 实现类
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginVo.getUsername(), loginVo.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);

        //覆盖认证错误抛出的信息 TODO 自定义一个Auth 异常 用全局异常捕获
        if(Objects.isNull(authentication)){
            throw new RuntimeException("用户名或者密码错误");
        }

        //密码比对正确继续执行 LoginUserDetails里面的 密码已经被security 清空
        //getPrincipal()，身份信息，大部分情况下返回的是UserDetails接口的实现类
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();

        //生成jwt
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username",userDetails.getUsername());

        String token = JwtUtil.generate(claims, expireTime, userDetails.getUsername());
//        System.out.println(token);
//        System.out.println(JwtUtil.getClaim(token));
        return  token;
    }


    @Override
    public void logout() {

    }

    @Override
    public Boolean sign(SignVo signVo) {
        return null;
    }
}
