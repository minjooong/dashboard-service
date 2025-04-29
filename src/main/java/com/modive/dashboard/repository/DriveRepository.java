package com.modive.dashboard.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
@RequiredArgsConstructor
public class DriveRepository {
    private final DynamoDbClient dynamoDbClient;

//    public void saveDriveData(User user) {
//        Map<String, AttributeValue> item = new HashMap<>();
//        item.put("userId", AttributeValue.fromS(user.getUserId()));
//        item.put("name", AttributeValue.fromS(user.getName()));
//
//        PutItemRequest request = PutItemRequest.builder()
//                .tableName("UserTable")
//                .item(item)
//                .build();
//
//        dynamoDbClient.putItem(request);
//    }
}
