package com.kw.nodeimageeditorbackend.examples.services;

import com.kw.nodeimageeditorbackend.examples.dto.ExampleCategoryDto;
import com.kw.nodeimageeditorbackend.examples.repositories.ExampleCategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleCategoryServiceImp implements ExampleCategoryService {

    private final ExampleCategoryRepo categoryRepo;

    public ExampleCategoryServiceImp(ExampleCategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<ExampleCategoryDto> getAll() {
        return categoryRepo.findAll();
    }
}
