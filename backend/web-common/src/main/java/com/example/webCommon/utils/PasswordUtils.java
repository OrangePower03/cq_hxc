package com.example.webCommon.utils;

import com.example.webCommon.utils.bean.SpringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {
    private static PasswordEncoder passwordEncoder;

    private static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder != null ? passwordEncoder :
                (passwordEncoder = SpringUtils.getBean(PasswordEncoder.class));
    }

    public static String encode(String rawPassword) {
        return getPasswordEncoder().encode(rawPassword);
    }

}
