package com.kw.nodeimageeditorbackend.utils.builders;

import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserBuilder {
    private static long userId = 1;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private UserEntity userEntity;

    public UserBuilder() {
        userEntity = new UserEntity();
        userEntity.setId(userId++);
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserEntity build() {
        var user = userEntity;
        this.userEntity = null;
        return user;
    }

    public UserBuilder setUsername(String username) {
        userEntity.setUsername(username);
        return this;
    }

    public UserBuilder setEmail(String email) {
        userEntity.setEmail(email);
        return this;
    }

    public UserBuilder setPassword(String password) {
        userEntity.setPassword(passwordEncoder.encode(password));
        return this;
    }

}
