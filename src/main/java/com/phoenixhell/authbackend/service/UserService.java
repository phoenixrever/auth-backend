package com.phoenixhell.authbackend.service;

import com.phoenixhell.authbackend.entity.UserEntity;
import com.phoenixhell.authbackend.entity.vo.UserRoleMenuVo;

import java.util.List;

/**
 * @author チヨウ　カツヒ
 * @date 2024-03-28 22:28
 */
public interface UserService {
    UserEntity selectByPrimaryKey(Long userid);
    UserEntity selectByUserName(String username);

    List<UserRoleMenuVo> getUserMenus(String username);

    List<String> getPermissions(String username);
}
