package com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImp;

import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.requests.SaveProjectRequest;
import com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImpTest;
import com.kw.nodeimageeditorbackend.utils.Box;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kw.nodeimageeditorbackend.utils.builders.ProjectBuilder.builder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

public class SaveProjectDataTest extends ProjectServiceImpTest {

    private ProjectEntity sampleProject;
    private SaveProjectRequest sampleSaveRequest;
    private Box<ProjectEntity> saveStorage;

    @BeforeEach
    void setUp() {
        saveStorage = new Box<>();

        sampleProject = builder(users.get(0), "project")
                .setData("data before modification")
                .addTags("Tag1", "Tag2")
                .addCollaborator(users.get(1), true, false)
                .build();

        sampleSaveRequest = new SaveProjectRequest();
        sampleSaveRequest.setProjectData("new project data");
        sampleSaveRequest.setProjectId(sampleProject.getId());
    }

    @Test
    public void should_change_project_data() {
        //given
        doReturn(Optional.of(sampleProject)).when(projectRepository).findById(sampleProject.getId());
        doAnswer(invocation -> {
            saveStorage.setObject(invocation.getArgument(0, ProjectEntity.class));
            return null;
        }).when(projectRepository).save(isA(ProjectEntity.class));

        //when
        projectServiceImp.saveProjectData(sampleSaveRequest, users.get(0).getId());

        //then
        var modifiedProject = saveStorage.getObject();
        assert modifiedProject.getProjectData().getProjectData().equals("new project data")
                && modifiedProject.hashCode() == sampleProject.hashCode();
    }

    @Test
    public void should_change_project_data2() {
        //given
        doReturn(Optional.of(sampleProject)).when(projectRepository).findById(sampleProject.getId());
        doAnswer(invocation -> {
            saveStorage.setObject(invocation.getArgument(0, ProjectEntity.class));
            return null;
        }).when(projectRepository).save(isA(ProjectEntity.class));

        //when
        projectServiceImp.saveProjectData(sampleSaveRequest, users.get(1).getId());

        //then
        var modifiedProject = saveStorage.getObject();
        assert modifiedProject.getProjectData().getProjectData().equals("new project data")
                && modifiedProject.hashCode() == sampleProject.hashCode();
    }

    @Test
    public void should_throw_AuthorizationException() throws Exception {
        //given
        doReturn(Optional.of(sampleProject)).when(projectRepository).findById(sampleProject.getId());


        //then
        assertThrows(AuthorizationException.class, () ->
                //when
                projectServiceImp.saveProjectData(sampleSaveRequest, users.get(2).getId())
        );
    }

    @Test
    public void should_throw_EntityNotExistException() throws Exception {
        //given
        doReturn(Optional.empty()).when(projectRepository).findById(Long.MAX_VALUE);


        //then
        assertThrows(EntityNotExistException.class, () ->
                //when
                projectServiceImp.saveProjectData(
                        new SaveProjectRequest(Long.MAX_VALUE, "new data"),
                        users.get(1).getId())
        );
    }
}
