package com.kw.nodeimageeditorbackend.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailsList {
    private List<ProjectDetailsDto> projectDetails;
    private Integer allResults;
    private Integer listStart;
}
