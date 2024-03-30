package com.phoenixhell.authbackend;

import com.phoenixhell.authbackend.entity.UserEntity;
import com.phoenixhell.authbackend.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@MapperScan(basePackages = "com.phoenixhell.authbackend.mapper")
@SpringBootApplication
public class AuthBackendApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(AuthBackendApplication.class, args);
        System.out.println("打断点通过容器run 查看security的filter链");
    }

}
