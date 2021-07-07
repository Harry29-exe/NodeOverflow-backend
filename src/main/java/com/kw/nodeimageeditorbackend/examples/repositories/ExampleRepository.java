package com.kw.nodeimageeditorbackend.examples.repositories;

import com.kw.nodeimageeditorbackend.examples.dto.ExampleDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExampleRepository extends MongoRepository<ExampleDto, String> {

    Optional<ExampleDto> findByCategoryAndName(String category, String name);

}
