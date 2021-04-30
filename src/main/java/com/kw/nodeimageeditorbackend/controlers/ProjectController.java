package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.responses.GetProjectsResponse;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import com.kw.nodeimageeditorbackend.services.project.ProjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("project")
    private ProjectDto getProject(
            @RequestParam Long projectId,
            @RequestParam(defaultValue = "false") Boolean withDetails) {

        return projectService.getProject(projectId);
    }


}
