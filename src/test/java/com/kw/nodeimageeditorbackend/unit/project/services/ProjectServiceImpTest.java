package com.kw.nodeimageeditorbackend.unit.project.services;

import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import com.kw.nodeimageeditorbackend.project.entities.AccessModifier;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.project.services.ProjectServiceImp;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import com.kw.nodeimageeditorbackend.utils.sampleListCreators.UserListCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.kw.nodeimageeditorbackend.utils.builders.ProjectBuilder.builder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public abstract class ProjectServiceImpTest {

    protected List<UserEntity> users;

    @Mock
    protected ProjectRepository projectRepository;
    @Mock
    protected UserRepository userRepository;
    @InjectMocks
    protected ProjectServiceImp projectServiceImp;

    PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @BeforeEach
    public void initEach() {
        var id = 1L;
        users = Arrays.asList(
                new UserEntity(id++, "bob", "bob@da", encoder.encode("pass"), null)
                ,new UserEntity(id++, "steve", "stevens@o2.de", encoder.encode("123"), null)
                ,new UserEntity(id++, "alex2", "al@we", encoder.encode("321"), null)
        );
    }






}