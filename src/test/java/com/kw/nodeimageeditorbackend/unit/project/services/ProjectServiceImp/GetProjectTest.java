package com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImp;

import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import com.kw.nodeimageeditorbackend.project.entities.AccessModifier;
import com.kw.nodeimageeditorbackend.unit.project.services.ProjectServiceImpTest;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.kw.nodeimageeditorbackend.utils.builders.ProjectBuilder.builder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

public class GetProjectTest extends ProjectServiceImpTest {

    @Test
    public void should_return_owners_project() throws Exception {
        //given
        var searchedProject = builder(users.get(0), "awesome project")
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
        var searchedProject = builder(users.get(0), "awesome project")
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
        var searchedProject = builder(users.get(0), "awesome project")
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
        var searchedProject = builder(users.get(0), "awesome project")
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

}
