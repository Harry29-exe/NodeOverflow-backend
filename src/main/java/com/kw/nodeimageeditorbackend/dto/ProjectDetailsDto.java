package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectDetailsDto {
    private ProjectMetadataDto metadata;
    private List<ProjectCollaboratorDto> collaborators;

    public ProjectDetailsDto(ProjectEntity entity) {
        this.metadata = new ProjectMetadataDto(entity);
        this.collaborators = getCollaborators(entity);
    }

    private List<ProjectCollaboratorDto> getCollaborators(ProjectEntity entity) {
        return entity.getCollaborators().stream()
                .map(ProjectCollaboratorDto::new).collect(Collectors.toList());
    }
}
