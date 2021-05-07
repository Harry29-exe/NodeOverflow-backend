package com.kw.nodeimageeditorbackend.project.requests;

import com.kw.nodeimageeditorbackend.project.entities.AccessModifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewProjectRequest {
    @NotEmpty
    private String title;
    @NotNull
    private AccessModifier accessModifier;
    private List<Long> collaboratorsIds;
    private List<String> tags;
    private String projectData;
}
