package com.kw.nodeimageeditorbackend.request.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequest {
    @NotEmpty
    private String title;

}
