package com.modive.dashboard.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@DynamoDBTable(tableName = "drive")
@Data
public class Drive {

    private String driveId;
    private Instant startTime;
    private Instant endTime;
    private int activeDriveDurationSec;

    @DynamoDBTypeConverted(converter = InstantListConverter.class)
    private List<Instant> suddenAccelerations;

    @DynamoDBTypeConverted(converter = InstantListConverter.class)
    private List<Instant> sharpTurns;

    private List<SpeedLog> speedLogs;
    private List<IdlingPeriod> idlingPeriods;

    private SpeedRate speedRate;
    private List<ReactionTime> reactionTimes;

    @DynamoDBTypeConverted(converter = InstantListConverter.class)
    private List<Instant> laneDepartures;

    private List<FollowingDistanceEvent> followingDistanceEvents;

    @DynamoDBTypeConverted(converter = InstantListConverter.class)
    private List<Instant> inactiveMoments;

    @DynamoDBHashKey(attributeName = "driveId")
    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    @DynamoDBTypeConverted(converter = InstantConverter.class)
    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    @DynamoDBTypeConverted(converter = InstantConverter.class)
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

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

    // 내부 클래스들 (모두 @DynamoDBDocument 추가)

    @DynamoDBDocument
    @Data
    public static class SpeedLog {
        private int period;
        private int maxSpeed;
    }

    @DynamoDBDocument
    @Data
    public static class IdlingPeriod {
        @DynamoDBTypeConverted(converter = InstantConverter.class)
        private Instant startTime;

        @DynamoDBTypeConverted(converter = InstantConverter.class)
        private Instant endTime;
    }

    @DynamoDBDocument
    @Data
    public static class SpeedRate {
        private int low;
        private int middle;
        private int high;
    }

    @DynamoDBDocument
    @Data
    public static class ReactionTime {
        @DynamoDBTypeConverted(converter = InstantConverter.class)
        private Instant detectedAt;

        @DynamoDBTypeConverted(converter = InstantConverter.class)
        private Instant reactedAt;
    }

    @DynamoDBDocument
    @Data
    public static class FollowingDistanceEvent {
        @DynamoDBTypeConverted(converter = InstantConverter.class)
        private Instant startTime;

        @DynamoDBTypeConverted(converter = InstantConverter.class)
        private Instant endTime;
    }
}