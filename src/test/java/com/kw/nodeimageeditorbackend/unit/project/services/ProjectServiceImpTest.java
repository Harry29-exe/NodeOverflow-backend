package com.kw.nodeimageeditorbackend.unit.project.services;

import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.services.ProjectServiceImp;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public abstract class ProjectServiceImpTest {

    protected List<UserEntity> users;

    @Mock
    protected ProjectRepository projectRepository;
    @Mock
    protected UserRepository userRepository;
    @InjectMocks
    protected ProjectServiceImp projectServiceImp;

    private final List<String> passwords = new ArrayList<>(3);


    public ProjectServiceImpTest() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(12);
        passwords.add(encoder.encode("pass"));
        passwords.add(encoder.encode("123"));
        passwords.add(encoder.encode("321"));
    }

    @BeforeEach
    public void initEach() {
        var id = 1L;
        users = Arrays.asList(
                new UserEntity(id++, "bob", "bob@da", passwords.get(0), null)
                , new UserEntity(id++, "steve", "stevens@o2.de", passwords.get(1), null)
                , new UserEntity(id++, "alex2", "al@we", passwords.get(2), null)
        );
    }


}