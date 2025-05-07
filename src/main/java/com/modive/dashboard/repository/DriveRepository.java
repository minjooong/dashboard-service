package com.modive.dashboard.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.modive.dashboard.entity.Drive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DriveRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public void save(Drive drive) {
        dynamoDBMapper.save(drive);
    }

    public Drive findById(String userId, String driveId) {
        System.out.printf("ℹ️ Finding drive by driveId: %s userId: %s%n", driveId, userId);

        return dynamoDBMapper.load(Drive.class, userId, driveId);
    }

    public void deleteById(String driveId) {
        Drive drive = new Drive();
        drive.setDriveId(driveId);
        dynamoDBMapper.delete(drive);
    }

    public List<Drive> findAll() {
        return dynamoDBMapper.scan(Drive.class, new DynamoDBScanExpression());
    }

    // 예: 특정 기간 이후 시작된 Drive 조회
    public List<Drive> findByStartTimeAfter(String isoStartTime) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":startTime", new AttributeValue().withS(isoStartTime));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("startTime > :startTime")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(Drive.class, scanExpression);
    }
}