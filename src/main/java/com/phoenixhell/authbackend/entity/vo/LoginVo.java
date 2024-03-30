package com.phoenixhell.authbackend.entity.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String username;
    private String password;
    private String code;
    private String captchaKey;
}
