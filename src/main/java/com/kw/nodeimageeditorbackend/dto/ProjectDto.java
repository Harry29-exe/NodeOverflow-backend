package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import com.kw.nodeimageeditorbackend.entities.project.ProjectTagEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String projectData;
    private UserDto owner;
    private ProjectMetadataDto projectDetails;
    private List<ProjectCollaboratorDto> collaborators;
    private List<String> tags;


    public ProjectDto(ProjectEntity entity) {
        this.projectId = entity.getId();
        this.projectDetails = new ProjectMetadataDto(entity, false);
        this.projectData = entity.getProjectData().getProjectData();
        this.owner = new UserDto(entity.getProjectOwner());
        this.collaborators = entity.getCollaborators().stream()
                .map(ProjectCollaboratorDto::new).collect(Collectors.toList());
        this.tags = entity.getTags().stream()
                .map(ProjectTagEntity::getContent)
                .collect(Collectors.toList());
    }

    public ProjectDto(ProjectEntity entity, boolean readAllData) {
        this.projectDetails = new ProjectMetadataDto(entity, false);
        this.owner = new UserDto(entity.getProjectOwner());
        if (readAllData) {
            this.projectData = entity.getProjectData().getProjectData();
            this.collaborators = entity.getCollaborators().stream()
                    .map(ProjectCollaboratorDto::new).collect(Collectors.toList());
            this.tags = entity.getTags().stream()
                    .map(ProjectTagEntity::getContent)
                    .collect(Collectors.toList());
        } else {
            this.projectData = null;
            //TODO ma sens?
            this.collaborators = new LinkedList<>();
            this.tags = new LinkedList<>();
        }
    }

    public ProjectDto(ProjectEntity entity, boolean readProjectData, boolean loadCollaborators, boolean loadTags) {
        this.projectDetails = new ProjectMetadataDto(entity, false);
        this.owner = new UserDto(entity.getProjectOwner());

        this.projectData = readProjectData ?
                entity.getProjectData().getProjectData()
                : null;
        this.collaborators = loadCollaborators ?
                entity.getCollaborators().stream()
                        .map(ProjectCollaboratorDto::new).collect(Collectors.toList())
                : new LinkedList<>();
        this.tags = loadTags ?
                entity.getTags().stream()
                        .map(ProjectTagEntity::getContent)
                        .collect(Collectors.toList())
                : new LinkedList<>();
    }
}
