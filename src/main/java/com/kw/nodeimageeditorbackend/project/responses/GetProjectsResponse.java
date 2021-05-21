package com.kw.nodeimageeditorbackend.project.responses;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetProjectsResponse {
    private List<ProjectDetailsDto> projects;
//    private Integer allResultsCount;
}
