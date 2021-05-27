package com.kw.nodeimageeditorbackend.unit.project.services;

import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import com.kw.nodeimageeditorbackend.project.entities.AccessModifier;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.services.ProjectServiceImp;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import com.kw.nodeimageeditorbackend.utils.sampleListCreators.UserListCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.kw.nodeimageeditorbackend.utils.builders.ProjectBuilder.builder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImpTest {

    private List<ProjectEntity> projectDatabase;
    private List<UserEntity> users;

    @Mock
    ProjectRepository projectRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ProjectServiceImp projectServiceImp;

    PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @BeforeEach
    public void initEach() {
        var id = 1L;
        users = Arrays.asList(
                new UserEntity(id++, "bob", "bob@da", encoder.encode("pass"), null)
                ,new UserEntity(id++, "steve", "stevens@o2.de", encoder.encode("123"), null)
                ,new UserEntity(id++, "alex2", "al@we", encoder.encode("321"), null)
        );

        projectDatabase = Arrays.asList(
                builder(users.get(0), "awesome project")
                        .addTag("tag1")
                        .setData("project data")
                        .setAccessModifier(AccessModifier.PUBLIC)
                        .addCollaborator(users.get(1))
                        .setCreationDate(new Date(119, Calendar.FEBRUARY, 20))
                        .setLastModifiedDate(new Date(120, Calendar.JUNE, 15))
                        .build()
//                ,builder(users.get(0))
        );
    }

    @Test
    public void should_return_owners_project() throws Exception {
        //given
        var searchedProject =  builder(users.get(0), "awesome project")
                .setAccessModifier(AccessModifier.PRIVATE)
                .build();
        var searchedProjectId = searchedProject.getId();
        doReturn(Optional.of(searchedProject)).when(projectRepository).findById(searchedProjectId);

        //when
        var foundProject = projectServiceImp.getProject(searchedProjectId, users.get(0).getId(), true);

        //then
        assertNotNull(foundProject);
        assertEquals(searchedProject.getId(), foundProject.getProjectId());
    }

    @Test
    public void should_return_collaborators_project() throws Exception {
        //given
        var searchedProject =  builder(users.get(0), "awesome project")
                .setAccessModifier(AccessModifier.PRIVATE)
                .addCollaborator(users.get(1))
                .build();
        var searchedProjectId = searchedProject.getId();
        doReturn(Optional.of(searchedProject)).when(projectRepository).findById(searchedProjectId);

        //when
        var foundProject = projectServiceImp.getProject(searchedProjectId, users.get(1).getId(), true);

        //then
        assertNotNull(foundProject);
        assertEquals(searchedProject.getId(), foundProject.getProjectId());
    }

    @Test
    public void should_return_public_project() throws Exception {
        //given
        var searchedProject =  builder(users.get(0), "awesome project")
                .setAccessModifier(AccessModifier.PUBLIC)
                .build();
        var searchedProjectId = searchedProject.getId();
        doReturn(Optional.of(searchedProject)).when(projectRepository).findById(searchedProjectId);

        //when
        var foundProject = projectServiceImp.getProject(searchedProjectId, Long.MAX_VALUE, true);

        //then
        assertNotNull(foundProject);
        assertEquals(searchedProject.getId(), foundProject.getProjectId());
    }

    @Test
    public void should_throw_AuthorizationException() throws Exception {
        //given
        var searchedProject =  builder(users.get(0), "awesome project")
                .setAccessModifier(AccessModifier.PRIVATE)
                .setCreationDate(new Date(119, Calendar.FEBRUARY, 20))
                .setLastModifiedDate(new Date(120, Calendar.JUNE, 15))
                .build();
        var searchedProjectId = searchedProject.getId();
        doReturn(Optional.of(searchedProject)).when(projectRepository).findById(searchedProjectId);

        //when
        //TODO convention break?
        assertThrows(AuthorizationException.class, () ->
                projectServiceImp.getProject(searchedProjectId, users.get(0).getId() + 1, true)
        );
    }

    @Test
    public void should_throw_EntityNotExistException() throws Exception {
        //given
        doReturn(Optional.empty()).when(projectRepository).findById(0L);

        //when
        assertThrows(EntityNotExistException.class, () ->
                projectServiceImp.getProject(0L, users.get(0).getId(), true)
        );
    }

//    @Test
//    public void
}