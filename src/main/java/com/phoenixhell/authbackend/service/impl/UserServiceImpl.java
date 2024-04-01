package com.phoenixhell.authbackend.service.impl;

import com.phoenixhell.authbackend.entity.UserEntity;
import com.phoenixhell.authbackend.entity.UserExample;
import com.phoenixhell.authbackend.entity.vo.UserRoleMenuVo;
import com.phoenixhell.authbackend.mapper.UserMapper;
import com.phoenixhell.authbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author チヨウ　カツヒ
 * @date 2024-03-28 22:29
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public UserEntity selectByPrimaryKey(Long userid) {
        return userMapper.selectByPrimaryKey(userid);
    }

    @Override
    public UserEntity selectByUserName(String  username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<UserEntity> userEntities = userMapper.selectByExample(userExample);
        if (userEntities != null && !userEntities.isEmpty()) {
            return userEntities.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<UserRoleMenuVo> getUserMenus(String username) {
        return null;
    }

    @Override
    public List<String> getPermissions(String username) {
        return null;
    }
}
