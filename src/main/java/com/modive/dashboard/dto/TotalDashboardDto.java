package com.modive.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TotalDashboardDto {
    private Long dashboardId;                // 누적 대시보드 ID

    private String userId;                   // 사용자 ID
    private LocalDateTime lastDrive;         // 최근 운전일
    private int driveCount;                  // 누적운전횟수

    private double totalScore;               // 종합점수
    private double ecoDrivingScore;          // 1. 탄소배출 점수
    private double idling;                   //    공회전 점수
    private double constantSpeedDriving;     //    정속주행 비율 점수
    private double safeDrivingScore;         // 2. 안전운전점수
    private double accelerationBraking;      //    급가/감속 점수
    private double sharpCornering;           //    급회전 점수
    private double speeding;                 //    과속 점수
    private double accidentPreventionScore;  // 3. 사고 예방 점수
    private double reactionTimes;            //    반응 속도 점수
    private double laneDeparture;            //    차선 이탈 점수
    private double safeDistance;             //    안전거리 유지 점수
    private double attentionScore;           // 4. 주의력 점수
    private double drivingTime;              //    운전시간 점수
    private double noOperationTime;          //    미조작 시간 점수

    private String feedback;                 // LLM 피드백

    private LocalDateTime createdAt;         // 최초 생성 일시
    private LocalDateTime modifiedAt;        // 업데이트 일시
}
