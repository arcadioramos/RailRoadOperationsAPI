package com.example.micronaut.repositories

import com.example.micronaut.entities.RailRoadCarDestinationEntity
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import java.util.*


@Singleton
class RailRoadCarDestinationRepository() : RailRoadCarCatalogRepository(){

    fun findById(id: String): Optional<RailRoadCarDestinationEntity> {
        val railRoadCarDestinationTable: DynamoDbTable<RailRoadCarDestinationEntity> = dynamoDbTable(RailRoadCarDestinationEntity::class.java)
        return findAllById(id, RailRoadCarDestinationEntity::class.java)
    }

    fun delete(id: String) {
        val railRoadCarDestinationTable: DynamoDbTable<RailRoadCarDestinationEntity> = dynamoDbTable(RailRoadCarDestinationEntity::class.java)
        railRoadCarDestinationTable.deleteItem(findById(id).get())
    }

    fun save(entity: RailRoadCarDestinationEntity): RailRoadCarDestinationEntity {
        val railRoadCarDestinationTable: DynamoDbTable<RailRoadCarDestinationEntity> = dynamoDbTable(RailRoadCarDestinationEntity::class.java)
        railRoadCarDestinationTable.putItem(entity)
        return entity;
    }

    fun update(entity: RailRoadCarDestinationEntity): RailRoadCarDestinationEntity {
        val railRoadCarDestinationTable: DynamoDbTable<RailRoadCarDestinationEntity> = dynamoDbTable(RailRoadCarDestinationEntity::class.java)
        railRoadCarDestinationTable.updateItem(entity)
        return entity;
    }

}