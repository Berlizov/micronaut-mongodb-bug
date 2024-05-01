package com.example;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@MappedEntity
public class Entity {
    @Id
    @GeneratedValue
    private String id;
    private final String simpleValue;
    private final ComplexValue complexValue;

    public Entity(String simpleValue, ComplexValue complexValue) {
        this.simpleValue = simpleValue;
        this.complexValue = complexValue;
    }

    @Serdeable
    public static class ComplexValue {
        private final String valueA;
        private final String valueB;

        public ComplexValue(String valueA, String valueB) {
            this.valueA = valueA;
            this.valueB = valueB;
        }

        public String getValueA() {
            return valueA;
        }

        public String getValueB() {
            return valueB;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSimpleValue() {
        return simpleValue;
    }

    public ComplexValue getComplexValue() {
        return complexValue;
    }
}
