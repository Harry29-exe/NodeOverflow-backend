package com.kw.nodeimageeditorbackend.project.requests;

import com.kw.nodeimageeditorbackend.project.entities.AccessModifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewProjectRequest {
    @NotEmpty
    private String title;
    @NotNull
    private AccessModifier accessModifier;
    private List<CreateNewProjectRequestCollaborator> collaborators = new LinkedList<>();
    private List<String> tags = new LinkedList<>();
    private String projectData;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateNewProjectRequestCollaborator {
        @NotNull
        private Long collaboratorId;
        private Boolean canWrite = false;
        private Boolean canFork = false;
    }
}
