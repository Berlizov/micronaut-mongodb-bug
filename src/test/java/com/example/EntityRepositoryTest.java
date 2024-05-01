package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class EntityRepositoryTest {
    @Inject
    EntityRepository repository;

    @Test
    void should_load_entity_by_id() { // Passed
        var savedEntity = new Entity("simpleValue", new Entity.ComplexValue("valueA", "valueB"));
        repository.save(savedEntity);

        var foundEntity = repository.findById(savedEntity.getId()).get();

        assertThat(foundEntity.getSimpleValue()).isEqualTo("simpleValue");
        assertThat(foundEntity.getComplexValue().getValueA()).isEqualTo("valueA");
        assertThat(foundEntity.getComplexValue().getValueB()).isEqualTo("valueB");
    }


    @Test
    void should_load_simple_value_by_id() { // Passed
        var savedEntity = new Entity("simpleValue", new Entity.ComplexValue("valueA", "valueB"));
        repository.save(savedEntity);

        var foundSimpleValue = repository.findSimpleValueById(savedEntity.getId()).get();

        assertThat(foundSimpleValue).isEqualTo("simpleValue");
    }

    @Test
    void should_load_complex_value_by_id() { // Failed
        var savedEntity = new Entity("simpleValue", new Entity.ComplexValue("valueA", "valueB"));
        repository.save(savedEntity);

        var foundComplexValue = repository.findComplexValueById(savedEntity.getId()).get(); // io.micronaut.data.exceptions.DataAccessException:
        //  Error instantiating type [com.example.Entity$ComplexValue] from introspection:
        //  Null argument specified for [valueA].
        //  If this argument is allowed to be null annotate it with @Nullable

        // AbstractMongoRepositoryOperations.convertResult
        // AbstractMongoRepositoryOperations:169
        // document is
        //     {"_id": {"$oid": "66320ff0fdb12a260ebadac3"}, "complexValue": {"valueA": "valueA", "valueB": "valueB"}}
        // but alias in "valueA", not "complexValue.valueA" or something like this.
        // so, it cannot find this alias in the document

        assertThat(foundComplexValue).isNotNull();
        assertThat(foundComplexValue.getValueA()).isEqualTo("valueA");
        assertThat(foundComplexValue.getValueB()).isEqualTo("valueB");
    }
}