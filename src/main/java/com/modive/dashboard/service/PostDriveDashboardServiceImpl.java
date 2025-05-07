package com.modive.dashboard.service;

import com.modive.dashboard.dto.DriveListDto;
import com.modive.dashboard.dto.ScoreDto;
import com.modive.dashboard.entity.Drive;
import com.modive.dashboard.entity.DriveDashboard;
import com.modive.dashboard.repository.DriveDashboardRepository;
import com.modive.dashboard.repository.DriveRepository;
import com.modive.dashboard.tools.ScoreCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PostDriveDashboardServiceImpl implements PostDriveDashboardService {
    @Autowired
    private DriveRepository driveRepository;
    @Autowired
    private ScoreCalculator scoreCalculator;
    @Autowired
    private DriveDashboardRepository driveDashboardRepository;

    // 1. 주행 후 대시보드 생성
    @Override
    public void createPostDriveDashboard(String userId, String driveId) {

        // 1-1. 데이터 가져와서 분석 후 저장
        Drive drive = getDummyDrive(userId, driveId); // TODO: driveId로 주행 데이터 가져오기 및 데이터 분석

        driveRepository.save(drive);

        // 1-2. 점수 산정 후 저장
        ScoreDto score = scoreCalculator.calculateDriveScore(drive);
        String feedback = "임시 피드백입니다."; // TODO: LLM 서비스에서 피드백 받아오기

        DriveDashboard dashboard = new DriveDashboard();

        dashboard.setUserId(userId);
        dashboard.setDriveId(driveId);
        dashboard.setStartTime(drive.getStartTime());
        dashboard.setEndTime(drive.getEndTime());
        dashboard.setScore(score);
        dashboard.setFeedback(feedback);

        driveDashboardRepository.save(dashboard);
    }

    // 2. 주행 후 대시보드 조회
    @Override
    public DriveDashboard getPostDriveDashboard(String userId, String driveId) {

        DriveDashboard dashboard = driveDashboardRepository.findById(userId, driveId);

        return dashboard;
    }

    // 4. 주행 후 대시보드 목록 조회
    @Override
    public List<DriveListDto> getPostDriveDashboardList(String userId) {

        List<DriveListDto> dtos = driveRepository.listByUserId(userId);

        return dtos;
    }

    // <editor-fold desc="# Get dummy data">
    private Drive getDummyDrive(String userId, String driveId) {
        Instant startTime = Instant.parse("2025-04-25T08:00:00Z");
        Instant endTime = Instant.parse("2025-04-25T10:00:00Z");

        Random random = new Random();

        Drive drive = new Drive();
        drive.setUserId(userId);
        drive.setDriveId(driveId);
        drive.setStartTime(startTime);
        drive.setEndTime(endTime);
        drive.setActiveDriveDurationSec(6900);

        // SuddenAccelerations
        drive.setSuddenAccelerations(generateRandomInstants(startTime, endTime, 10));

        // SharpTurns
        drive.setSharpTurns(generateRandomInstants(startTime, endTime, 10));

        // SpeedLogs
        List<Drive.SpeedLog> speedLogs = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Drive.SpeedLog log = new Drive.SpeedLog();
            log.setPeriod(i);
            log.setMaxSpeed(30 + random.nextInt(90));  // 30~120 km/h
            speedLogs.add(log);
        }
        drive.setSpeedLogs(speedLogs);

        // IdlingPeriods
        List<Drive.IdlingPeriod> idlingPeriods = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Instant s = startTime.plusSeconds(i * 600);
            Drive.IdlingPeriod p = new Drive.IdlingPeriod();
            p.setStartTime(s);
            p.setEndTime(s.plusSeconds(120));
            idlingPeriods.add(p);
        }
        drive.setIdlingPeriods(idlingPeriods);

        // SpeedRate
        Drive.SpeedRate speedRate = new Drive.SpeedRate();
        speedRate.setLow(25);
        speedRate.setMiddle(65);
        speedRate.setHigh(10);
        drive.setSpeedRate(speedRate);

        // ReactionTimes
        List<Drive.ReactionTime> reactionTimes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Instant d = startTime.plusSeconds(i * 1020);
            Drive.ReactionTime r = new Drive.ReactionTime();
            r.setDetectedAt(d);
            r.setReactedAt(d.plusSeconds(2));
            reactionTimes.add(r);
        }
        drive.setReactionTimes(reactionTimes);

        // LaneDepartures
        drive.setLaneDepartures(generateRandomInstants(startTime, endTime, 6));

        // FollowingDistanceEvents
        List<Drive.FollowingDistanceEvent> followingEvents = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Instant s = startTime.plusSeconds(i * 720);
            Drive.FollowingDistanceEvent e = new Drive.FollowingDistanceEvent();
            e.setStartTime(s);
            e.setEndTime(s.plusSeconds(20));
            followingEvents.add(e);
        }
        drive.setFollowingDistanceEvents(followingEvents);

        // InactiveMoments
        drive.setInactiveMoments(generateRandomInstants(startTime, endTime, 6));

        return drive;
    }
    private List<Instant> generateRandomInstants(Instant start, Instant end, int count) {
        List<Instant> instants = new ArrayList<>();
        long startEpoch = start.getEpochSecond();
        long endEpoch = end.getEpochSecond();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            long randomEpoch = startEpoch + (long) (random.nextDouble() * (endEpoch - startEpoch));
            instants.add(Instant.ofEpochSecond(randomEpoch));
        }

        instants.sort(Comparator.naturalOrder());
        return instants;
    }
    // </editor-fold>
}
