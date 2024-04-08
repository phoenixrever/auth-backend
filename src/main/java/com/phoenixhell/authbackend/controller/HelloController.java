package com.phoenixhell.authbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author チヨウ　カツヒ
 * @date 2024-03-26 22:31
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        System.out.println(details); //WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null]
        return "你通过token 访问到了 api hello 接口";
    }
    @PreAuthorize("hasAuthority('menu:list')")
    @GetMapping("/menus")
    public String testAuthority() {
        return "你访问了 menus 页面 menu:list 的权限";
    }
}
