package com.example.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.fishfish.oauth2.EnableAuthorizationServer;
import tk.fishfish.oauth2.EnableResourceServer;

/**
 * 认证服务，注意 {@link EnableAuthorizationServer} 必须在 {@link EnableResourceServer} 前面使用
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
public class AuthorizationApplication {

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT", "8080");
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
