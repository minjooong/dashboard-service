package com.modive.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class DriveDashboardDto {
    private String driveId;
    private Instant startTime;
    private Instant endTime;
    private int activeDriveDurationSec;

    private List<Instant> suddenAccelerations;
    private List<Instant> sharpTurns;

    private List<SpeedLog> speedLogs;

    private List<IdlingPeriod> idlingPeriods;

    private SpeedRate speedRate;

    private List<ReactionTime> reactionTimes;
    private List<Instant> laneDepartures;

    private List<FollowingDistanceEvent> followingDistanceEvents;
    private List<Instant> inactiveMoments;

    @Data
    @Builder
    public static class SpeedLog {
        private int period;
        private int maxSpeed;
    }

    @Data
    @Builder
    public static class IdlingPeriod {
        private Instant startTime;
        private Instant endTime;
    }

    @Data
    @Builder
    public static class SpeedRate {
        private int low;
        private int middle;
        private int high;
    }

    @Data
    @Builder
    public static class ReactionTime {
        private Instant detectedAt;
        private Instant reactedAt;
    }

    @Data
    @Builder
    public static class FollowingDistanceEvent {
        private Instant startTime;
        private Instant endTime;
    }
}

