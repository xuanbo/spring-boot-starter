package com.example.crypto;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 测试加密
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class BcryptTest {

    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void encode() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);

        boolean matches = passwordEncoder.matches("123456", encode);
        System.out.println(matches);
    }

}
