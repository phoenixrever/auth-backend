package com.phoenixhell.authbackend.service;

import com.phoenixhell.authbackend.entity.vo.LoginVo;
import com.phoenixhell.authbackend.entity.vo.SignVo;


/**
 * @author phoenixhell
 * @since 2022/1/27 0027-下午 2:58
 */

public interface LoginService {
    String login(LoginVo loginVo);

    void logout();

    Boolean sign(SignVo signVo);
}
