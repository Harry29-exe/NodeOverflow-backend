package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.request.project.CreateProjectRequest;
import com.kw.nodeimageeditorbackend.responses.GetProjectsResponse;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import com.kw.nodeimageeditorbackend.services.project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
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
    public GetProjectsResponse getProjects() {
        ApplicationUserDetails userDetails = (ApplicationUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new GetProjectsResponse(projectService.getUserProjectsDetails(userDetails.getId()));
    }

    //TODO ask var
    //TODO security layer
    @GetMapping("project")
    public ProjectDto getProject(
            @RequestParam Long projectId,
            @RequestParam(defaultValue = "false") Boolean withDetails,
            Authentication authorization) {

//        var userDetails = getAuthorizationDetails();
        var userDetails = (ApplicationUserDetails) authorization.getDetails();
        var project = projectService.getProject(projectId, withDetails);


        var accessMod = project.getProjectDetails().getAccessModifier();
        if (!(accessMod == PUBLIC || accessMod == PROTECTED ||
                getPermissions(project, userDetails.getId()).contains(READ))) {
            throw new AuthorizationException("You do not have permission to read this project");
        }

        return project;
    }

    @PostMapping("project")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProject(@RequestBody @Valid CreateProjectRequest request) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
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
