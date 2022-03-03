package com.example.micronaut.repositories
import com.example.micronaut.entities.RailRoadCarReceiverEntity
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import java.util.*

@Singleton
class RailRoadCarReceiverRepository(): RailRoadCarCatalogRepository(){

    fun findById(id: String): Optional<RailRoadCarReceiverEntity> {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        return findAllById(id, RailRoadCarReceiverEntity::class.java)
    }

    fun delete(id: String) {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        railRoadCarReceiverTable.deleteItem(findById(id).get())
    }

    fun save(entity: RailRoadCarReceiverEntity): RailRoadCarReceiverEntity {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        railRoadCarReceiverTable.putItem(entity)
        return entity;
    }

    fun update(entity: RailRoadCarReceiverEntity): RailRoadCarReceiverEntity {
        val railRoadCarReceiverTable: DynamoDbTable<RailRoadCarReceiverEntity> = dynamoDbTable(RailRoadCarReceiverEntity::class.java)
        railRoadCarReceiverTable.updateItem(entity)
        return entity;
    }


}