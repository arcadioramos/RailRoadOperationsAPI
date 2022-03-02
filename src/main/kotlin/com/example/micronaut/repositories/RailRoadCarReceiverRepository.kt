package com.example.micronaut.repositories
import com.example.micronaut.entities.RailRoadCarReceiverEntity
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import com.example.micronaut.helpers.PersistenceHelper
import jakarta.inject.Singleton
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.services.dynamodb.model.*
import java.net.URI
import java.util.*

@Singleton
class RailRoadCarReceiverRepository(): RailRoadCarCatalogRepository(){

    fun findById(id: String): Optional<RailRoadCarReceiverEntity> {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        return Optional.of(railRoadCarReceiverTable.getItem(Key.builder()
                                                               .partitionValue(AttributeValue.builder()
                                                                              .s(id).build()).build()))
    }

     fun findAll(): MutableIterable<RailRoadCarReceiverEntity> {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        return railRoadCarReceiverTable.scan().items()
    }

    fun delete(entity: RailRoadCarReceiverEntity) {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        railRoadCarReceiverTable.deleteItem(entity)
    }

    fun save(entity: RailRoadCarReceiverEntity): RailRoadCarReceiverEntity {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        railRoadCarReceiverTable.putItem(entity)
        return entity;
    }


}