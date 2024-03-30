package com.phoenixhell.authbackend.service.impl;

import com.phoenixhell.authbackend.entity.LoginUserDetails;
import com.phoenixhell.authbackend.entity.UserEntity;
import com.phoenixhell.authbackend.service.UserService;
import com.phoenixhell.authbackend.utils.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DaoAuthenticationProvider会去对比 UserDetailsService返回的userDetail 对象提取 其中的数据库返回的用户密码与用户提交的密码(加密后)
 * (Authentication implements UsernamePasswordAuthenticationToken)是否匹配
 *
 * 会调用  passwordEncoder.matches() 比对2着密码是否相同
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    //userDetails 序列化有点问题这里就用List<String> 光存权限 不存类了
    //@Resource
    //private RedisTemplate<String,List<String>> redisTemplate;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.selectByUserName(username);

        /**
         * 这边必须要检查userEntity是否为空 因为
         *  UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
         *  调用我们自定义的 loadUserByUsername 方法的时候 loadedUser是不为空的只是里面的UserEntity 是空
         *  后面调用additionalAuthenticationChecks loadedUser.getPassword() 比对密码的时候会空异常(因为里面的UserEntity为空)
         */
        if(Objects.isNull(userEntity)){
            //在这里抛出的异常 SpringSecurity 会被 ExceptionTranslationFilter 自动捕获并转换UsernameNotFoundException为BadCredentialsException
            //也可以直接排除 RuntimeException 因为 UsernameNotFoundException -> AuthenticationException -> RuntimeException
            //throw new UsernameNotFoundException("无此用户"); 抛出错误是Bad credentials 不太明确 不使用
            throw new RuntimeException(ExceptionCode.LOGIN_EXCEPTION.getMessage());
        }

        //TODO 查询对应的权限信息 
        List<String> permissions = new ArrayList<>();

        LoginUserDetails userDetails = new LoginUserDetails(userEntity, permissions);

        return userDetails;
    }
}


