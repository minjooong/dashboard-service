package com.modive.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostDriveDashboardResponse {
    private Instant startTime;
    private Instant endTime;

    private float totalScore;
    private float ecoScore;
    private float safeScore;
    private float preventionScore;
    private float attentionScore;

    private String feedback;
}
