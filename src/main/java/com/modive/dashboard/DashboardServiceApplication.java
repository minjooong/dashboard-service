package com.modive.dashboard;

import com.modive.dashboard.config.DynamoDbTableCreator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootApplication
public class DashboardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(DynamoDbClient dynamoDbClient) {
		return args -> {
			// 테이블 생성 확인 및 생성
			DynamoDbTableCreator.createDriveTableIfNotExists(dynamoDbClient);
		};
	}
}
