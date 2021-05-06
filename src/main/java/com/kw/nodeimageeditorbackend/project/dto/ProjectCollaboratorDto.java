package com.kw.nodeimageeditorbackend.project.dto;

import com.kw.nodeimageeditorbackend.project.entities.ProjectCollaboratorEntity;
import lombok.Data;

@Data
public class ProjectCollaboratorDto {
    private String collaboratorUsername;
    private Long collaboratorId;
    private boolean canWrite;
    private boolean canFork;

    public ProjectCollaboratorDto(ProjectCollaboratorEntity entity) {
        this.collaboratorUsername = entity.getCollaborator().getUsername();
        this.canWrite = entity.getCanWrite();
        this.canFork = entity.getCanFork();
        this.collaboratorId = entity.getId();
    }
}
