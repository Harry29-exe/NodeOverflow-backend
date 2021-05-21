package com.kw.nodeimageeditorbackend.project.controllers;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsList;
import com.kw.nodeimageeditorbackend.project.requests.GetFilteredProjectDetailsRequest;
import com.kw.nodeimageeditorbackend.project.responses.GetProjectsResponse;
import com.kw.nodeimageeditorbackend.project.services.ProjectDetailsService;
import com.kw.nodeimageeditorbackend.project.services.ProjectService;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class ProjectDetailsController {
    private final ProjectService projectService;
    private final ProjectDetailsService projectDetailsService;

    public ProjectDetailsController(ProjectService projectService, ProjectDetailsService projectDetailsService) {
        this.projectService = projectService;
        this.projectDetailsService = projectDetailsService;
    }

    @GetMapping("projects/details")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDetailsList getProjects(
            @RequestParam(defaultValue = "10") Integer resultsPerPage,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "false") Boolean findCollaborationProjects
    ) {
        ApplicationUserDetails userDetails = (ApplicationUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return projectDetailsService
                .getUserProjectsDetails(
                        userDetails.getId(),
                        page,
                        resultsPerPage,
                        findCollaborationProjects);
    }

    @PostMapping("projects/details")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDetailsList filterAndGetProjects(
            @RequestBody @Valid GetFilteredProjectDetailsRequest request,
            @RequestParam(defaultValue = "10") Integer resultsPerPage,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "false") Boolean findCollaborationProjects,
            Authentication authentication
    ) {
        var userDetails = (ApplicationUserDetails) authentication.getPrincipal();

        return projectDetailsService
                .searchProjects(
                        request,
                        userDetails.getId(),
                        page, resultsPerPage,
                        findCollaborationProjects);
    }

    @PatchMapping("project/details")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProjectDetails() {

    }

}
