package com.phoenixhell.authbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    //spring boot 跨域设置  spring security 跨域还要在其配置类中单独配置http.cors()
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  //允许的域名 不能是.allowedOrigin
                .allowCredentials(true) //是否允许携带cookie
                .allowedHeaders("*") //允许的请求头
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }
}
