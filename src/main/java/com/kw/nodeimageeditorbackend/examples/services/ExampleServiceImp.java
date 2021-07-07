package com.kw.nodeimageeditorbackend.examples.services;

import com.kw.nodeimageeditorbackend.examples.dto.ExampleDto;
import com.kw.nodeimageeditorbackend.examples.repositories.ExampleRepository;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImp implements ExampleService {

    private final ExampleRepository exampleRepository;

    public ExampleServiceImp(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public ExampleDto getExample(String name, String category) {
        return exampleRepository.findByCategoryAndName(category, name).orElseThrow(EntityNotExistException::new);
    }

    @Override
    public void createExample(ExampleDto example) {

    }

    @Override
    public void deleteExample(ExampleDto exampleDto) {

    }
}
