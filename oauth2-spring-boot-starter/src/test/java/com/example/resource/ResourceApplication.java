package com.example.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.fishfish.oauth2.EnableResourceServer;

/**
 * 资源服务
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@EnableResourceServer
@SpringBootApplication
public class ResourceApplication {

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT", "9090");
        SpringApplication.run(ResourceApplication.class, args);
    }

}
