package com.kw.nodeimageeditorbackend.responses;

import com.kw.nodeimageeditorbackend.dto.ProjectDetailsDto;
import lombok.Data;

import java.util.List;

@Data
public class GetProjectsResponse {
    private List<ProjectDetailsDto> projects;

    public GetProjectsResponse(List<ProjectDetailsDto> projects) {
        this.projects = projects;
    }
}
