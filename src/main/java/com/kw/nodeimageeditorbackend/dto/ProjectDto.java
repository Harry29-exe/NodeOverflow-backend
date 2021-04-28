package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private String title;
//    private


    public ProjectDto(ProjectEntity projectEntity) {
        this.title = projectEntity.getTitle();
    }
}
