package com.kw.nodeimageeditorbackend.examples.repositories;

import com.kw.nodeimageeditorbackend.examples.dto.ExampleCategoryDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleCategoryRepo extends MongoRepository<ExampleCategoryDto, String> {


}
