package com.kw.nodeimageeditorbackend.examples.services;

import com.kw.nodeimageeditorbackend.examples.dto.ExampleDto;

public interface ExampleService {

    ExampleDto getExample(String name, String category);

    void createExample(ExampleDto example);

    void deleteExample(ExampleDto exampleDto);


}
