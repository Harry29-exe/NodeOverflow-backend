package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.enums.AccessModifier;
import com.kw.nodeimageeditorbackend.entities.project.ProjectDataEntity;
import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import com.kw.nodeimageeditorbackend.entities.project.ProjectTag;
import lombok.Data;

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
        this.tags = entity.getTags().stream()
                .map(ProjectTag::getContent)
                .collect(Collectors.toList());
        this.accessModifier = entity.getAccessModifier();
    }
}
