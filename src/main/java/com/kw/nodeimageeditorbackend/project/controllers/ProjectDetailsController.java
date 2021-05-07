package com.kw.nodeimageeditorbackend.project.controllers;

import com.kw.nodeimageeditorbackend.project.responses.GetProjectsResponse;
import com.kw.nodeimageeditorbackend.project.services.ProjectService;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ProjectDetailsController {
    private final ProjectService projectService;

    public ProjectDetailsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("projects/details")
    @ResponseStatus(HttpStatus.OK)
    public GetProjectsResponse getProjects() {
        ApplicationUserDetails userDetails = (ApplicationUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new GetProjectsResponse(projectService.getUserProjectsDetails(userDetails.getId()));
    }

//    @PostMapping("projects/details")
//    @ResponseStatus(HttpStatus.OK)
//    public GetProjectsResponse filterAndGetProjects() {
//
//    }

    @PatchMapping("project/details")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProjectDetails() {

    }

}
