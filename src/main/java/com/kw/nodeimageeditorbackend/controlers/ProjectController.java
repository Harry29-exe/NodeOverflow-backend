package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.responses.GetProjectsResponse;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import com.kw.nodeimageeditorbackend.services.project.ProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static com.kw.nodeimageeditorbackend.controlers.ProjectController.Permission.*;
import static com.kw.nodeimageeditorbackend.entities.enums.AccessModifier.PROTECTED;
import static com.kw.nodeimageeditorbackend.entities.enums.AccessModifier.PUBLIC;

@RestController
@RequestMapping("/api/")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("projects")
    private GetProjectsResponse getProjects() {
        ApplicationUserDetails userDetails = (ApplicationUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new GetProjectsResponse(projectService.getUserProjectsDetails(userDetails.getId()));
    }

    //TODO ask var
    //TODO security layer
    @GetMapping("project")
    private ProjectDto getProject(
            @RequestParam Long projectId,
            @RequestParam(defaultValue = "false") Boolean withDetails) {

        var userDetails = getAuthorizationDetails();
        var project = projectService.getProject(projectId, withDetails);


        var accessMod = project.getProjectDetails().getAccessModifier();
        if (!(accessMod == PUBLIC || accessMod == PROTECTED ||
                getPermissions(project, userDetails.getId()).contains(READ))) {
            throw new AuthorizationException("You do not have permission to read this project");
        }

        return project;
    }

    //TODO ask
    private ApplicationUserDetails getAuthorizationDetails() {
        return (ApplicationUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getDetails();
    }

    private List<Permission> getPermissions(ProjectDto project, Long userID) {
        if (project.getOwner().getId().equals(userID)) {
            return List.of(READ, WRITE, FORK);
        }

        for (var user : project.getCollaborators()) {
            if (user.getCollaboratorId().equals(userID)) {
                var permissions = new LinkedList<Permission>();
                permissions.add(READ);
                if (user.isCanFork()) {
                    permissions.add(FORK);
                }
                if (user.isCanWrite()) {
                    permissions.add(WRITE);
                }
                return permissions;
            }
        }

        return new LinkedList<>();
    }

    public enum Permission {
        READ,
        WRITE,
        FORK
    }
}
