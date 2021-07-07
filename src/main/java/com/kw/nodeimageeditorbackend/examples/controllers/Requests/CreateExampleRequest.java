package com.kw.nodeimageeditorbackend.examples.controllers.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CreateExampleRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String description;

    @NotBlank
    private String userProjectToLink;

}
