package com.example.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.fishfish.admin.configuration.EnableAdmin;
import tk.fishfish.oauth2.EnableResourceServer;

/**
 * 启动
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@EnableAdmin
@EnableResourceServer
@SpringBootApplication
public class AdminResourceApplication {

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT", "9090");
        SpringApplication.run(AdminResourceApplication.class, args);
    }

}
