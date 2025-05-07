package com.modive.dashboard.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class TypeConverter {

    // Instant 변환기
    public static class InstantConverter implements DynamoDBTypeConverter<String, Instant> {
        @Override
        public String convert(Instant object) {
            return object.toString();
        }

        @Override
        public Instant unconvert(String object) {
            return Instant.parse(object);
        }
    }

    // List<Instant> 변환기
    public static class InstantListConverter implements DynamoDBTypeConverter<List<String>, List<Instant>> {
        @Override
        public List<String> convert(List<Instant> instants) {
            return instants.stream().map(Instant::toString).collect(Collectors.toList());
        }

        @Override
        public List<Instant> unconvert(List<String> strings) {
            return strings.stream().map(Instant::parse).collect(Collectors.toList());
        }
    }

}
