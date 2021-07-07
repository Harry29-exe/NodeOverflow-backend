package com.kw.nodeimageeditorbackend.examples.controllers;

import com.kw.nodeimageeditorbackend.examples.dto.ExampleCategoryDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/examples/")
@CrossOrigin
public class ExampleListController {

    @GetMapping("info")
    public List<ExampleCategoryDto> getCategories() {
        return List.of(new ExampleCategoryDto("Introduction", List.of("Basic concepts", "Links", "Adding nodes")));
    }
}
