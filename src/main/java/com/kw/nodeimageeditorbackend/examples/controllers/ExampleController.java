package com.kw.nodeimageeditorbackend.examples.controllers;

import com.kw.nodeimageeditorbackend.examples.controllers.Requests.CreateExampleRequest;
import com.kw.nodeimageeditorbackend.examples.dto.ExampleDto;
import com.kw.nodeimageeditorbackend.examples.repositories.ExampleRepository;
import com.kw.nodeimageeditorbackend.examples.services.ExampleService;
import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.services.ProjectService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/example/")
@CrossOrigin
public class ExampleController {

    private ExampleService exampleService;
    private ProjectService projectService;

    public ExampleController(ExampleService exampleService, ProjectService projectService) {
        this.exampleService = exampleService;
        this.projectService = projectService;
    }

    @GetMapping("{id}")
    public ExampleDto getExample(@PathVariable String id) {
        return new ExampleDto("Basic concepts", "Introduction", "Description", "{\"viewProperties\":{\"canvasViewProps\":{\"scale\":1,\"shiftTop\":0,\"shiftLeft\":0},\"editorSplitRatio\":0.6,\"nodeSelectorWidth\":214},\"nodes\":[{\"name\":\"Output node\",\"id\":0,\"nodeViewProps\":{\"dimensions\":{\"width\":160,\"headHeight\":26,\"segmentHeight\":22,\"footerHeight\":22},\"x\":195.015625,\"y\":-124.5},\"segmentSaves\":[{\"segmentIndex\":0,\"segmentValue\":{}}]},{\"name\":\"Image input\",\"id\":1,\"nodeViewProps\":{\"dimensions\":{\"width\":160,\"headHeight\":26,\"segmentHeight\":22,\"footerHeight\":22},\"x\":-347.25710227272725,\"y\":-117.5},\"segmentSaves\":[{\"segmentIndex\":0},{\"segmentIndex\":1,\"segmentValue\":null}]}],\"links\":[{\"inputNodeId\":0,\"inputSegmentIndex\":0,\"outputNodeId\":1,\"outputSegmentIndex\":0}]}");
    }

    @PostMapping
    public void createExample(@Valid CreateExampleRequest request) {

    }
}
