package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProjectDto {
    private ProjectMetadataDto metadata;
    private byte[] projectData;
    private List<ProjectCollaboratorDto> collaborators;

    public ProjectDto(ProjectEntity entity) {
        this.metadata = new ProjectMetadataDto(entity);
        this.projectData = entity.getProjectData().getProjectData();
        this.collaborators = getCollaborators(entity);
        System.out.println(new String(projectData));
    }

    public ProjectDto(ProjectEntity entity, boolean withProjectData, boolean withCollaborators) {
        this.metadata = new ProjectMetadataDto(entity);
        if (withProjectData) {
            projectData = entity.getProjectData().getProjectData();
        }
        if(withCollaborators) {
            collaborators = getCollaborators(entity);
        }
    }

    private List<ProjectCollaboratorDto> getCollaborators(ProjectEntity entity) {
        return entity.getCollaborators().stream()
                .map(ProjectCollaboratorDto::new).collect(Collectors.toList());
    }
}
