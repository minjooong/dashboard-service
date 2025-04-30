package com.modive.dashboard.service;

import com.modive.dashboard.entity.Drive;
import com.modive.dashboard.repository.DriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PostDriveDashboardServiceImpl implements PostDriveDashboardService {
    @Autowired
    private DriveRepository driveRepository;

    @Override
    public void createPostDriveDashboard(Long userId, Long driveId) {

        // TODO: driveId로 주행 데이터 가져오기 및 데이터 분석

        // 임시 데이터 생성
        Drive drive = Drive.builder()
                .driveId(UUID.randomUUID().toString())
                .startTime(Instant.now().minusSeconds(900))  // 15분 전
                .endTime(Instant.now())
                .activeDriveDurationSec(780)
                .suddenAccelerations(List.of(
                        Instant.now().minusSeconds(800),
                        Instant.now().minusSeconds(400)
                ))
                .sharpTurns(List.of(
                        Instant.now().minusSeconds(600)
                ))
                .speedLogs(List.of(
                        Drive.SpeedLog.builder().period(60).maxSpeed(80).build(),
                        Drive.SpeedLog.builder().period(120).maxSpeed(95).build()
                ))
                .idlingPeriods(List.of(
                        Drive.IdlingPeriod.builder()
                                .startTime(Instant.now().minusSeconds(850))
                                .endTime(Instant.now().minusSeconds(840))
                                .build()
                ))
                .speedRate(Drive.SpeedRate.builder()
                        .low(30)
                        .middle(50)
                        .high(20)
                        .build())
                .reactionTimes(List.of(
                        Drive.ReactionTime.builder()
                                .detectedAt(Instant.now().minusSeconds(500))
                                .reactedAt(Instant.now().minusSeconds(498))
                                .build()
                ))
                .laneDepartures(List.of(
                        Instant.now().minusSeconds(700)
                ))
                .followingDistanceEvents(List.of(
                        Drive.FollowingDistanceEvent.builder()
                                .startTime(Instant.now().minusSeconds(600))
                                .endTime(Instant.now().minusSeconds(590))
                                .build()
                ))
                .inactiveMoments(List.of(
                        Instant.now().minusSeconds(300),
                        Instant.now().minusSeconds(200)
                ))
                .build();

        driveRepository.save(drive);
    }

}
