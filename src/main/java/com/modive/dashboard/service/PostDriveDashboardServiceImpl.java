package com.modive.dashboard.service;

import com.modive.dashboard.entity.Drive;
import com.modive.dashboard.repository.DriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class PostDriveDashboardServiceImpl implements PostDriveDashboardService {
    @Autowired
    private DriveRepository driveRepository;

    @Override
    public void createPostDriveDashboard(Long userId, Long driveId) {

        // TODO: driveId로 주행 데이터 가져오기 및 데이터 분석
        // 일단 Dummy Data 가져오기
        Drive drive = getDummyDrive(userId, driveId);

        driveRepository.save(drive);
    }

    private Drive getDummyDrive(Long userId, Long driveId)
    {
        Instant now = Instant.now();
        Instant startTime = now.minusSeconds(600); // 10분 전
        Instant endTime = now;

        // SpeedLog
        Drive.SpeedLog speedLog = new Drive.SpeedLog();
        speedLog.setPeriod(60);
        speedLog.setMaxSpeed(85);

        // IdlingPeriod
        Drive.IdlingPeriod idlingPeriod = new Drive.IdlingPeriod();
        idlingPeriod.setStartTime(startTime.plusSeconds(100));
        idlingPeriod.setEndTime(startTime.plusSeconds(150));

        // SpeedRate
        Drive.SpeedRate speedRate = new Drive.SpeedRate();
        speedRate.setLow(30);
        speedRate.setMiddle(50);
        speedRate.setHigh(20);

        // ReactionTime
        Drive.ReactionTime reactionTime = new Drive.ReactionTime();
        reactionTime.setDetectedAt(startTime.plusSeconds(200));
        reactionTime.setReactedAt(startTime.plusSeconds(202));

        // FollowingDistanceEvent
        Drive.FollowingDistanceEvent followingEvent = new Drive.FollowingDistanceEvent();
        followingEvent.setStartTime(startTime.plusSeconds(300));
        followingEvent.setEndTime(startTime.plusSeconds(330));

        // Drive 객체 생성
        Drive drive = new Drive();
        drive.setDriveId(driveId.toString());
        drive.setStartTime(startTime);
        drive.setEndTime(endTime);
        drive.setActiveDriveDurationSec(540);
        drive.setSuddenAccelerations(Collections.singletonList(startTime.plusSeconds(60)));
        drive.setSharpTurns(Collections.singletonList(startTime.plusSeconds(120)));
        drive.setSpeedLogs(Collections.singletonList(speedLog));
        drive.setIdlingPeriods(Collections.singletonList(idlingPeriod));
        drive.setSpeedRate(speedRate);
        drive.setReactionTimes(Collections.singletonList(reactionTime));
        drive.setFollowingDistanceEvents(Collections.singletonList(followingEvent));
        drive.setLaneDepartures(Collections.singletonList(startTime.plusSeconds(400)));
        drive.setInactiveMoments(Arrays.asList(startTime.plusSeconds(450), startTime.plusSeconds(500)));

        return drive;
    }

}
