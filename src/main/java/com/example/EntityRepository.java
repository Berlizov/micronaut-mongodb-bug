package com.example;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@MongoRepository
public interface EntityRepository extends CrudRepository<Entity, String> {
    Optional<String> findSimpleValueById(String id);
    Optional<Entity.ComplexValue> findComplexValueById(String id);
}
