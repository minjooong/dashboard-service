package com.modive.dashboard.controller;

import com.modive.dashboard.entity.Drive;
import com.modive.dashboard.enums.ScoreType;
import com.modive.dashboard.service.PostDriveDashboardService;
import com.modive.dashboard.service.TotalDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/dashboard/post-drive")
public class PostDriveDashboardController {

    @Autowired
    private PostDriveDashboardService postDriveDashboardService;
    @Autowired
    private TotalDashboardService totalDashboardService;

    // 1. 주행 후 대시보드 생성 및 누적 대시보드 업데이트 (주행 완료 처리)
    @PostMapping("/{driveId}")
    public ResponseEntity<Void> createPostDriveDashboard(
            @RequestHeader("X-User-Id") String userId, // TODO: userId 연동
            @PathVariable String driveId
    ) {

        // TODO: 예외 처리하기
        postDriveDashboardService.createPostDriveDashboard(userId, driveId);
        //totalDashboardService.updateTotalDashboard(driveId);

        return ResponseEntity.noContent().build();
    }

    // 2. 주행 후 대시보드 조회
    @GetMapping("/{driveId}")
    public ResponseEntity<Object> getPostDriveDashboard(
            @RequestHeader("X-User-Id") String userId, // TODO: userId 연동
            @PathVariable String driveId
    ) {

        Drive drive = postDriveDashboardService.getPostDriveDashboard(userId, driveId);
        return drive == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(drive);
    }

    // 3. 주행 후 대시보드 상세 조회 (safe, eco, prevention, attention)


    // 4. 주행 후 대시보드 목록 조회



}
