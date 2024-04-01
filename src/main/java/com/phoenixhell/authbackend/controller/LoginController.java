package com.phoenixhell.authbackend.controller;

import com.phoenixhell.authbackend.entity.vo.LoginVo;
import com.phoenixhell.authbackend.service.LoginService;
import com.phoenixhell.authbackend.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author チヨウ　カツヒ
 * @date 2024-03-30 17:07
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    // context-path: /api controller 中 api 就可以省略
    //不要忘记security 放行/login （也不需要加 /api）
    @PostMapping({"/login"})
    //public R login(@RequestBody  String json) {
    public R login(@RequestBody LoginVo loginVo) {

        String token = loginService.login(loginVo);
        return R.ok().put("token", token);
    }
}
