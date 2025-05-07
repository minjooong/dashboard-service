package com.modive.dashboard.tools;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modive.dashboard.dto.ScoreDto;
import org.springframework.context.annotation.Bean;

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

    // ScoreDto 변환기
    public static class ScoreDtoConverter implements DynamoDBTypeConverter<String, ScoreDto> {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convert(ScoreDto scoreDto) {
            try {
                return objectMapper.writeValueAsString(scoreDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize ScoreDto", e);
            }
        }

        @Override
        public ScoreDto unconvert(String scoreJson) {
            try {
                return objectMapper.readValue(scoreJson, ScoreDto.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize ScoreDto", e);
            }
        }
    }

}
