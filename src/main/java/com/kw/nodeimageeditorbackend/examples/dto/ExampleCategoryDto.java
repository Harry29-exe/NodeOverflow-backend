package com.kw.nodeimageeditorbackend.examples.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExampleCategoryDto {

    private String name;
    private List<String> sections;
}
