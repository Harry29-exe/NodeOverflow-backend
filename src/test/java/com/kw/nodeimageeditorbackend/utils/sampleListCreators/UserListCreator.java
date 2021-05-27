package com.kw.nodeimageeditorbackend.utils.sampleListCreators;

import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserListCreator {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public static List<UserEntity> createSampleUserList() {
        Long id = 1L;

        return Arrays.asList(
                new UserEntity(id++, "alex", "alex@gm.com", passwordEncoder.encode("password"), new LinkedList<>())
                ,new UserEntity(id++, "alice", "alice@t2.ls", passwordEncoder.encode("111"), new LinkedList<>())
                ,new UserEntity(id++, "world destroyer", "alex@gm.com", passwordEncoder.encode("drowssap"), new LinkedList<>())
                ,new UserEntity(id++, "ann", "alex@gm.com", passwordEncoder.encode("pass123"), new LinkedList<>())
                ,new UserEntity(id++, "bob", "awesome@a", passwordEncoder.encode("333"), new LinkedList<>())
        );
    }
}
