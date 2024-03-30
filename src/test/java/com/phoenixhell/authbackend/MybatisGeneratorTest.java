package com.phoenixhell.authbackend;

import com.phoenixhell.authbackend.entity.UserEntity;
import com.phoenixhell.authbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author チヨウ　カツヒ
 * @date 2024-03-28 21:44
 */
@SpringBootTest
public class MybatisGeneratorTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Test
    public  void test(){
        UserEntity userEntity = userService.selectByPrimaryKey(1L);
        System.out.println(userEntity);
    }

    @Test
    public  void test2(){
        UserEntity userEntity = userService.selectByUserName("admin");
        System.out.println(userEntity);
    }

    @Test
    public  void test3(){
        String password = passwordEncoder.encode("123456");
        //内部会成随机的salt 每次加密的结果都不一样
        System.out.println(password);//$2a$10$UO1TyK6esugLSv95qNiZ1..i1s//OxoWNEEMppbovwrsCS0/r1c36.

        System.out.println(passwordEncoder.matches("123456", password));
    }
}
