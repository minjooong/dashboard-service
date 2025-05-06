package com.modive.dashboard.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class DynamoDBTableInitializer {

    private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "local") // 리전은 임의로 넣어도 됨
            )
            .build();

    private static final String TABLE_NAME = "drive";

    @PostConstruct
    public void createTableIfNotExists() {
        ListTablesResult tables = dynamoDB.listTables();
        if (!tables.getTableNames().contains(TABLE_NAME)) {
            CreateTableRequest request = new CreateTableRequest()
                    .withTableName(TABLE_NAME)
                    .withKeySchema(new KeySchemaElement("driveId", KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition("driveId", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

            dynamoDB.createTable(request);
            System.out.println("✅ DynamoDB 테이블 'drive' 생성됨");
        } else {
            System.out.println("ℹ️ DynamoDB 테이블 'drive' 이미 존재함");
        }
    }
}