package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private String projectData;
    private ProjectDetailsDto projectDetails;


    public ProjectDto(ProjectEntity entity) {
        this.projectDetails = new ProjectDetailsDto(entity);
        this.projectData = entity.getProjectData().getProjectData();
    }

    public ProjectDto(ProjectEntity entity, boolean withProjectDetails) {
        projectData = entity.getProjectData().getProjectData();

        if (withProjectDetails) {
            this.projectDetails = new ProjectDetailsDto(entity);
        }
    }


}
