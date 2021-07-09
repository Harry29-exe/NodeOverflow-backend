package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import com.kw.nodeimageeditorbackend.project.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.project.entities.ProjectCollaboratorEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectDataEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectTagEntity;
import com.kw.nodeimageeditorbackend.project.enums.ProjectPermission;
import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.project.requests.SaveProjectRequest;
import com.kw.nodeimageeditorbackend.security.user.AppAuthentication;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.kw.nodeimageeditorbackend.project.entities.AccessModifier.PROTECTED;
import static com.kw.nodeimageeditorbackend.project.entities.AccessModifier.PUBLIC;
import static com.kw.nodeimageeditorbackend.project.enums.ProjectPermission.*;

@Service
public class ProjectServiceImp implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImp(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectDto getProject(Long projectId, AppAuthentication authentication, boolean withProjectDetails) {
        var projectEntity = projectRepository.findById(projectId)
                .orElseThrow(EntityNotExistException::new);
        var project = new ProjectDto(projectEntity, withProjectDetails);

        if (!hasPermission(projectEntity, authentication.getPrincipal(), READ)) {
            throw new AuthorizationException("You do not have permission to read this project");
        }

        return project;
    }

    @Override
    public void createProject(CreateNewProjectRequest request, AppAuthentication authentication) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setTitle(request.getTitle());
        projectEntity.setCreationDate(new Date());
        projectEntity.setLastModified(new Date());
        projectEntity.setAccessModifier(request.getAccessModifier());

        UserEntity owner = new UserEntity();
        owner.setId(authentication.getPrincipal());
        projectEntity.setProjectOwner(owner);

        ProjectDataEntity dataEntity = new ProjectDataEntity();
        dataEntity.setProjectData(request.getProjectData());
        dataEntity.setProject(projectEntity);
        projectEntity.setProjectData(dataEntity);

        List<ProjectCollaboratorEntity> collaborators = new LinkedList<>();
        request.getCollaborators().forEach(collaborator -> {
            var collaboratorEntity = new ProjectCollaboratorEntity();
            var userEntity = new UserEntity();
            userEntity.setId(collaborator.getCollaboratorId());
            collaboratorEntity.setCollaborator(userEntity);
            collaboratorEntity.setCanFork(collaborator.getCanFork());
            collaboratorEntity.setCanWrite(collaborator.getCanWrite());
            collaboratorEntity.setProject(projectEntity);
            collaborators.add(collaboratorEntity);
        });
        projectEntity.setCollaborators(collaborators);

        List<ProjectTagEntity> tags = new LinkedList<>();
        request.getTags().forEach(tag -> {
            var tagEntity = new ProjectTagEntity();
            tagEntity.setProject(projectEntity);
            tagEntity.setContent(tag);
            tags.add(tagEntity);
        });
        projectEntity.setTags(tags);

        projectRepository.save(projectEntity);
    }

    @Override
    public void saveProjectData(SaveProjectRequest request, AppAuthentication authentication) {
        var projectToModify = projectRepository.findById(request.getProjectId())
                .orElseThrow(EntityNotExistException::new);
        if (!hasPermission(projectToModify, authentication.getPrincipal(), WRITE)) {
            throw new AuthorizationException();
        }

        projectToModify.getProjectData().setProjectData(request.getProjectData());
        projectToModify.setLastModified(new Date());

        projectRepository.save(projectToModify);
    }

    @Override
    public void updateProjectDetails(ProjectDto projectDto, AppAuthentication authentication) {

    }

    @Override
    public void deleteProject(Long projectId, AppAuthentication authentication) {
        var projectEntity = projectRepository.findById(projectId)
                .orElseThrow(EntityNotExistException::new);

        if (!projectEntity.getProjectOwner().getId().equals(authentication.getPrincipal())) {
            throw new AuthorizationException("Only project owner can delete project");
        }

        projectRepository.delete(projectEntity);
    }


    private boolean hasPermission(
            ProjectEntity projectEntity,
            Long userId,
            ProjectPermission permission) {

        var accessMod = projectEntity.getAccessModifier();

        return userId.equals(projectEntity.getProjectOwner().getId()) ||
                permission == READ && (accessMod == PUBLIC || accessMod == PROTECTED) ||
                getPermissions(projectEntity, userId).contains(permission);
    }

    private List<ProjectPermission> getPermissions(ProjectEntity project, Long userID) {
        if (project.getProjectOwner().getId().equals(userID)) {
            return List.of(READ, WRITE, FORK);
        }

        for (var user : project.getCollaborators()) {
            if (user.getCollaborator().getId().equals(userID)) {
                var permissions = new LinkedList<ProjectPermission>();
                permissions.add(READ);
                if (user.getCanFork()) {
                    permissions.add(FORK);
                }
                if (user.getCanWrite()) {
                    permissions.add(WRITE);
                }
                return permissions;
            }
        }

        return new LinkedList<>();
    }
}
