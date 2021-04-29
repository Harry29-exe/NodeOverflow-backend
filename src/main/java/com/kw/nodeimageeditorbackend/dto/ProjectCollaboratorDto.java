package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.project.ProjectCollaboratorEntity;
import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import lombok.Data;

import java.util.List;

@Data
public class ProjectCollaboratorDto {
    private String collaboratorUsername;
    private boolean canWrite;
    private boolean canFork;

    public ProjectCollaboratorDto(ProjectCollaboratorEntity entity) {
        this.collaboratorUsername = entity.getCollaborator().getUsername();
        this.canWrite = entity.getCanWrite();
        this.canFork = entity.getCanFork();
    }
}
