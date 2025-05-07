package com.modive.dashboard.service;

import com.modive.dashboard.dto.DriveListDto;
import com.modive.dashboard.dto.PostDriveDashboardResponse;
import com.modive.dashboard.entity.Drive;

import java.util.List;

public interface PostDriveDashboardService {

    // 1. 주행 후 대시보드 생성
    void createPostDriveDashboard(String userId, String driveId);

    // 2. 주행 후 대시보드 조회
    Drive getPostDriveDashboard(String userId, String driveId);

//    // 3. 주행 후 대시보드 상세 조회 (safe, eco, prevention, attention)
//    // TODO: 점수별 상세 조회 반환 타입 DTO 정하기
//    Object getPostDriveDashboardByType(Long driveId, ScoreType type);
//
    // 4. 주행 후 대시보드 목록 조회
    List<DriveListDto> getPostDriveDashboardList(String userId);
}
