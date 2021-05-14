package com.kw.nodeimageeditorbackend.project.controllers;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.project.requests.SaveProjectRequest;
import com.kw.nodeimageeditorbackend.project.services.ProjectService;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("project")
    public ProjectDto getProject(
            @RequestParam Long projectId,
            @RequestParam(defaultValue = "false") Boolean withDetails,
            Authentication authorization) {

        var userDetails = (ApplicationUserDetails) authorization.getPrincipal();

        return projectService.getProject(projectId, userDetails.getId(), withDetails);
    }

    @PostMapping("project")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(
            @RequestBody @Valid CreateNewProjectRequest request,
            Authentication authentication) {

        var id = ((ApplicationUserDetails) authentication.getPrincipal()).getId();
        projectService.createProject(request, id);
    }

    @PatchMapping("project")
    @ResponseStatus(HttpStatus.OK)
    public void saveProject(
            @RequestBody @Valid SaveProjectRequest request,
            Authentication authentication) {

        var id = ((ApplicationUserDetails) authentication.getPrincipal()).getId();
        projectService.saveProjectData(request, id);
    }

    @DeleteMapping("project")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(
            @RequestParam Long projectId,
            Authentication authentication
    ) {
        var id = ((ApplicationUserDetails) authentication.getPrincipal()).getId();
        projectService.deleteProject(projectId, id);
    }


}
