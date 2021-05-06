package com.kw.nodeimageeditorbackend.project.dto;

import com.kw.nodeimageeditorbackend.project.entities.AccessModifier;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectTagEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectMetadataDto {
    private String title;
    private Date creationDate;
    private Date lastModified;
    private List<String> tags;
    private AccessModifier accessModifier;

    public ProjectMetadataDto(ProjectEntity entity) {
        this.title = entity.getTitle();
        this.creationDate = entity.getCreationDate();
        this.lastModified = entity.getCreationDate();
        this.tags = readTags(entity);
        this.accessModifier = entity.getAccessModifier();
    }

    public ProjectMetadataDto(ProjectEntity entity, boolean readTags) {
        this.title = entity.getTitle();
        this.creationDate = entity.getCreationDate();
        this.lastModified = entity.getCreationDate();
        this.accessModifier = entity.getAccessModifier();
        if (readTags) {
            this.tags = readTags(entity);
        } else {
            this.tags = new ArrayList<>(0);
        }
    }

    private List<String> readTags(ProjectEntity entity) {
        return entity.getTags().stream()
                .map(ProjectTagEntity::getContent)
                .collect(Collectors.toList());
    }
}
