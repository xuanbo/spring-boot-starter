package com.example.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.fishfish.admin.configuration.EnableAdmin;
import tk.fishfish.oauth2.EnableAuthorizationServer;
import tk.fishfish.oauth2.EnableResourceServer;

/**
 * 启动
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@EnableAdmin
@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
public class AdminAuthorizationApplication {

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT", "8080");
        SpringApplication.run(AdminAuthorizationApplication.class, args);
    }

}
