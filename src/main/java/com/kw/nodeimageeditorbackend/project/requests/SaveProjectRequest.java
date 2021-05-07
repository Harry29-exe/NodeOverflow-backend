package com.kw.nodeimageeditorbackend.project.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveProjectRequest {
    @NotNull
    private Long projectId;
    @NotEmpty
    private String projectData;
}
