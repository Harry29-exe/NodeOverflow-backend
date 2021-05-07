package com.kw.nodeimageeditorbackend.project.dto;

import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectTagEntity;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.user.dto.UserDto;
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


    public ProjectDto(ProjectEntity entity) {
        this.projectId = entity.getId();
        this.projectDetails = new ProjectMetadataDto(entity, true);
        this.projectData = entity.getProjectData().getProjectData();
        this.owner = new UserDto(entity.getProjectOwner());
        this.collaborators = entity.getCollaborators().stream()
                .map(ProjectCollaboratorDto::new).collect(Collectors.toList());
    }

    public ProjectDto(ProjectEntity entity, boolean readAllData) {
        this.projectDetails = new ProjectMetadataDto(entity, readAllData);
        this.owner = new UserDto(entity.getProjectOwner());
        if (readAllData) {
            this.projectData = entity.getProjectData().getProjectData();
            this.collaborators = entity.getCollaborators().stream()
                    .map(ProjectCollaboratorDto::new).collect(Collectors.toList());
        } else {
            this.projectData = "";
            //TODO ma sens?
            this.collaborators = new LinkedList<>();
        }
    }

    public ProjectDto(ProjectEntity entity, boolean readProjectData, boolean loadCollaborators, boolean loadTags) {
        this.projectDetails = new ProjectMetadataDto(entity, loadTags);
        this.owner = new UserDto(entity.getProjectOwner());

        this.projectData = readProjectData ?
                entity.getProjectData().getProjectData()
                : null;
        this.collaborators = loadCollaborators ?
                entity.getCollaborators().stream()
                        .map(ProjectCollaboratorDto::new).collect(Collectors.toList())
                : new LinkedList<>();
    }


}
