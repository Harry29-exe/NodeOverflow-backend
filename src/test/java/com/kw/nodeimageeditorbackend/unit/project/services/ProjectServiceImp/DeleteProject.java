package com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImp;

import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.kw.nodeimageeditorbackend.utils.builders.ProjectBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class DeleteProject extends ProjectServiceImpTest {

    private List<ProjectEntity> projectList;

    @BeforeEach
    public void initProjectList() {
        projectList = List.of(
                builder(users.get(0), "awesome project").build()
                ,builder(users.get(2), "proj of u2").build()
                ,builder(users.get(1), "user1's project").build()
                ,builder(users.get(0), "awesome project2").build()
                ,builder(users.get(1), "u1 proj").build()
        );
    }

    @Test
    void should_delete_project() {
        //given
        var projectToRemove = projectList.get(0);
        var removedProjectId = new AtomicLong(Long.MIN_VALUE);
        doReturn(Optional.of(projectToRemove)).when(projectRepository).findById(projectToRemove.getId());
        doAnswer(inv -> {
            removedProjectId.set(((ProjectEntity) inv.getArgument(0)).getId());
            return null;
        }).when(projectRepository).delete(isA(ProjectEntity.class));

        //when
        projectServiceImp.deleteProject(projectToRemove.getId(), projectToRemove.getProjectOwner().getId());

        //then
        assertEquals(projectToRemove.getId(), removedProjectId.get());
    }

    @Test
    void should_throw_AuthorizationException() {
        //given
        var projectToRemove = projectList.get(0);
        doReturn(Optional.of(projectToRemove)).when(projectRepository).findById(projectToRemove.getId());

        //then
        assertThrows(AuthorizationException.class, () ->
                //when
            projectServiceImp.deleteProject(projectToRemove.getId(), users.get(1).getId()));
    }

    @Test
    void should_throw_EntityNotExistException() {
        //given
        doReturn(Optional.empty()).when(projectRepository).findById(Long.MAX_VALUE);

        //then
        assertThrows(EntityNotExistException.class, () ->
                //when
                projectServiceImp.deleteProject(Long.MAX_VALUE, users.get(0).getId()));
    }
}
