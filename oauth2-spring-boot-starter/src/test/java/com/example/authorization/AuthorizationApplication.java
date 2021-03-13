package com.example.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.fishfish.oauth2.configuration.EnableAuthorizationServer;
import tk.fishfish.oauth2.configuration.EnableResourceServer;

/**
 * 认证服务
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication
public class AuthorizationApplication {

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT", "8080");
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
