package com.kw.nodeimageeditorbackend.project.dto;

import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectDetailsDto {
    private Long id;
    private ProjectMetadataDto metadata;
    private List<ProjectCollaboratorDto> collaborators;

    public ProjectDetailsDto(ProjectEntity entity) {
        this.id = entity.getId();
        this.metadata = new ProjectMetadataDto(entity);
        this.collaborators = getCollaborators(entity);
    }

    private List<ProjectCollaboratorDto> getCollaborators(ProjectEntity entity) {
        return entity.getCollaborators().stream()
                .map(ProjectCollaboratorDto::new).collect(Collectors.toList());
    }
}
