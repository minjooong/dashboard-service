package com.modive.dashboard.repository;

import com.modive.dashboard.entity.Drive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriveRepository {

    private final DynamoDbEnhancedClient enhancedClient;

    private final String TABLE_NAME = "drive";

    private DynamoDbTable<Drive> table() {
        return enhancedClient.table(TABLE_NAME, TableSchema.fromBean(Drive.class));
    }

    public void save(Drive drive) {
        table().putItem(drive);
    }

    public Optional<Drive> findById(String driveId) {
        Drive key = Drive.builder().driveId(driveId).build();
        Drive result = table().getItem(r -> r.key(k -> k.partitionValue(driveId)));
        return Optional.ofNullable(result);
    }

    public void deleteById(String driveId) {
        table().deleteItem(r -> r.key(k -> k.partitionValue(driveId)));
    }
}
