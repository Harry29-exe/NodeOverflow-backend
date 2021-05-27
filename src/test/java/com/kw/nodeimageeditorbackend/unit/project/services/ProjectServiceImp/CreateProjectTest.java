package com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImp;

import com.kw.nodeimageeditorbackend.project.entities.AccessModifier;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.project.services.ProjectServiceImp;
import com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImpTest;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;

public class CreateProjectTest extends ProjectServiceImpTest {

    @Test
    public void should_create_and_save_new_project() throws Exception {
        //given
        var request = new CreateNewProjectRequest();
        request.setProjectData("project data");
        request.setTitle("title");
        request.setAccessModifier(AccessModifier.PROTECTED);

        List<ProjectEntity> savedProject = new LinkedList<>();
        doAnswer(invocation -> {
            savedProject.add(invocation.getArgument(0, ProjectEntity.class));
            return null;
        }).when(projectRepository).save(isA(ProjectEntity.class));

        //when
        projectServiceImp.createProject(request, 0L);

        //then
        var project = savedProject.get(0);
        assert project.getProjectOwner().getId() == 0L
                && project.getTitle().equals("title")
                && project.getProjectData().getProjectData().equals("project data")
                && project.getAccessModifier().equals(AccessModifier.PROTECTED)
                && project.getTags().size() == 0
                && project.getCollaborators().size() == 0;
    }
}
