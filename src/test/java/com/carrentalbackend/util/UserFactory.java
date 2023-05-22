package com.carrentalbackend.util;

import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.model.enumeration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserFactory {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static String simpleUserEmail = "simple@mail.com";
    public static String simpleUserPassword = "simple password";
    public static Role simpleUserRole = Role.ADMIN;

    public static User getSimpleUser() {
        return new User(0L, simpleUserEmail, passwordEncoder.encode(simpleUserPassword), simpleUserRole);
    }
}
