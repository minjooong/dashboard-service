package com.modive.dashboard.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.modive.dashboard.tools.TypeConverter;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@DynamoDBTable(tableName = "drive")
@Data
public class Drive {
    // Partition Key
    private String userId;
    // Sort Key
    private String driveId;


    @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
    private Instant startTime;

    @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
    private Instant endTime;

    private int activeDriveDurationSec;

    @DynamoDBTypeConverted(converter = TypeConverter.InstantListConverter.class)
    private List<Instant> suddenAccelerations;

    @DynamoDBTypeConverted(converter = TypeConverter.InstantListConverter.class)
    private List<Instant> sharpTurns;

    private List<SpeedLog> speedLogs;

    private List<IdlingPeriod> idlingPeriods;

    private SpeedRate speedRate;

    private List<ReactionTime> reactionTimes;

    @DynamoDBTypeConverted(converter = TypeConverter.InstantListConverter.class)
    private List<Instant> laneDepartures;

    private List<FollowingDistanceEvent> followingDistanceEvents;

    @DynamoDBTypeConverted(converter = TypeConverter.InstantListConverter.class)
    private List<Instant> inactiveMoments;


    // getter
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBRangeKey(attributeName = "driveId")
    public String getDriveId() {
        return driveId;
    }


    // 내부 클래스
    @DynamoDBDocument
    @Data
    public static class SpeedLog {
        private int period;
        private int maxSpeed;
    }

    @DynamoDBDocument
    @Data
    public static class IdlingPeriod {
        @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
        private Instant startTime;

        @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
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
        @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
        private Instant detectedAt;

        @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
        private Instant reactedAt;
    }

    @DynamoDBDocument
    @Data
    public static class FollowingDistanceEvent {
        @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
        private Instant startTime;

        @DynamoDBTypeConverted(converter = TypeConverter.InstantConverter.class)
        private Instant endTime;
    }
}