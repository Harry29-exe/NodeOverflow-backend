package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.services.project.ProjectService;
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
    private List<ProjectDto> getProjects(@RequestParam Long userId) {
        return projectService.getUserProjects(userId);
    }


}
