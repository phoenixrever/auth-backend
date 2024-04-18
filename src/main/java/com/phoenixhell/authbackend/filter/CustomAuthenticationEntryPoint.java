package com.phoenixhell.authbackend.filter;

import com.alibaba.fastjson.JSON;

import com.phoenixhell.authbackend.utils.ExceptionCode;
import com.phoenixhell.authbackend.utils.R;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 *  ExceptionTranslationFilter catch到AccessDeniedException后，分为两种情况：
 *     如果用户处于未登录（anonymous）状态，会先触发AuthenticationEntryPoint，如果没有配置，则会重定向至登录页；
 *     如果用户处于登陆（authenticated）状态，会触发AccessDeniedHandler。
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 使用handlerExceptionResolver 传递异常到控制层捕获
    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("从filter转发异常到controller层抛出："+authException);
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}