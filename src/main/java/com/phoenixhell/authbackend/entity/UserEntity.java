package com.phoenixhell.authbackend.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity {
    private Long userId;

    private String username;

    private String nickName;

    private Boolean gender;

    private String phone;

    private String email;

    private String avatar;

    private String password;

    private String roles;

    private Boolean enabled;

    private String createdBy;

    private String updatedBy;

    private Date pwdResetTime;

    private Date createdTime;

    private Date updatedTime;
}