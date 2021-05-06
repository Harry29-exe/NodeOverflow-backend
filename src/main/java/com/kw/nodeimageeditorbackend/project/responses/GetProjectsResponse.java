package com.kw.nodeimageeditorbackend.project.responses;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsDto;
import lombok.Data;

import java.util.List;

@Data
public class GetProjectsResponse {
    private List<ProjectDetailsDto> projects;

    public GetProjectsResponse(List<ProjectDetailsDto> projects) {
        this.projects = projects;
    }
}
