package com.example.micronaut.repositories

import com.example.micronaut.entities.RailRoadCarDestinationEntity
import com.example.micronaut.helpers.PersistenceHelper
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest
import software.amazon.awssdk.regions.internal.util.ServiceMetadataUtils.partitionKey
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.*


@Singleton
class RailRoadCarDestinationRepository() : RailRoadCarCatalogRepository(){

    fun findById(id: String): Optional<RailRoadCarDestinationEntity> {
        val railRoadCarDestinationTable: DynamoDbTable<RailRoadCarDestinationEntity> = dynamoDbTable(RailRoadCarDestinationEntity::class.java)
        return Optional.of(railRoadCarDestinationTable.getItem(Key.builder()
                                                               .partitionValue(AttributeValue.builder()
                                                                              .s(id).build()).build()))
    }

    fun delete(entity: RailRoadCarDestinationEntity) {
        val railRoadCarDestinationTable: DynamoDbTable<RailRoadCarDestinationEntity> = dynamoDbTable(RailRoadCarDestinationEntity::class.java)
        railRoadCarDestinationTable.deleteItem(entity)
    }

    fun save(entity: RailRoadCarDestinationEntity): RailRoadCarDestinationEntity {
        val railRoadCarDestinationTable: DynamoDbTable<RailRoadCarDestinationEntity> = dynamoDbTable(RailRoadCarDestinationEntity::class.java)
        railRoadCarDestinationTable.putItem(entity)
        return entity;
    }

}