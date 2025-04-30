package com.modive.dashboard.config;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

public class DynamoDbTableCreator {

    private static final String TABLE_NAME = "drive";

    public static void createDriveTableIfNotExists(DynamoDbClient dynamoDbClient) {
        try {
            // 테이블이 이미 존재하는지 확인
            ListTablesResponse listTablesResponse = dynamoDbClient.listTables();
            if (listTablesResponse.tableNames().contains(TABLE_NAME)) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
                return;
            }

            // 테이블 생성 요청
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(TABLE_NAME)
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("driveId")
                            .keyType(KeyType.HASH)
                            .build())
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("driveId")
                            .attributeType(ScalarAttributeType.S)
                            .build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)  // 온디맨드 방식
                    .build();


            // 테이블 생성
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table " + TABLE_NAME + " created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating table " + TABLE_NAME);
        }
    }
}
