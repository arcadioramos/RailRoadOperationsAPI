package com.example.micronaut.helpers

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException
import java.net.URI

class PersistenceHelper {
    companion object {
        fun createTable(
            tableNameVal: String,
            dynamoDbClient: DynamoDbClient,
            createTableRequest: CreateTableRequest
        ): String {
            var createdTable: CreateTableResponse?
            var resultStr: String
            try {
                createdTable = dynamoDbClient.createTable(createTableRequest)
                checkTableStatus(dynamoDbClient, tableNameVal)
                resultStr = createdTable.tableDescription().tableArn()
                println("${tableNameVal} Table is created")
            } catch (e: ResourceInUseException) {
                println("${tableNameVal} Table is already on database")
                resultStr = ""
            }

            return resultStr
        }

        fun dynamoDbClient(): DynamoDbClient {
            val envRegion = "us-east-1"
            val region = Region.of(envRegion)

            return DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                // The region is meaningless for local DynamoDb but required for client builder validation
                .region(Region.US_EAST_1)
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("dummy-key", "dummy-secret")
                    )
                )
                .build()
        }

        private fun checkTableStatus(ddb: DynamoDbClient, tableNameVal: String): String {
            var describeTableRequest = DescribeTableRequest.builder()
                .tableName(tableNameVal)
                .build()

            ddb.waiter().waitUntilTableExists(describeTableRequest)
                .matched()
                .response()
                .ifPresent(System.out::println)
            return tableNameVal

        }

    }
}
