package com.vijay.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String ssm = bCryptPasswordEncoder.encode("ssm");
        System.out.println(ssm);
    }
}
