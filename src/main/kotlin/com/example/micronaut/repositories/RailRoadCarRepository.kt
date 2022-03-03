package com.example.micronaut.repositories
import io.micronaut.data.repository.CrudRepository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import com.example.micronaut.entities.RailRoadCarEntity
import com.example.micronaut.helpers.PersistenceHelper
import jakarta.inject.Singleton
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.waiters.WaiterResponse
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.services.dynamodb.model.*
import java.net.URI
import java.util.*

@Singleton
class RailRoadCarRepository{
    fun findById(id: String): Optional<RailRoadCarEntity> {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        return Optional.of(railRoadCarTable.scan().items().filter{ x -> x.id.equals(id) }.toList().first())
    }

    fun findByName(name: String): Optional<RailRoadCarEntity> {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        return Optional.of(railRoadCarTable.scan().items().filter{ x -> x.name.equals(name) }.toList().first())
    }

     fun findAll(): MutableIterable<RailRoadCarEntity> {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        return railRoadCarTable.scan().items()
    }
    fun delete(entity: RailRoadCarEntity) {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        railRoadCarTable.deleteItem(entity)
    }

    fun update(entity: RailRoadCarEntity): RailRoadCarEntity {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        railRoadCarTable.updateItem(entity)
        return entity;
    }

    fun save(entity: RailRoadCarEntity): RailRoadCarEntity {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        railRoadCarTable.putItem(entity)
        return entity;
    }
    private fun dynamoDbTable(): DynamoDbTable<RailRoadCarEntity> {
        val tableName = "RailRoadCar"
        val dynamoDbClientEnhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(PersistenceHelper.dynamoDbClient())
            .build()
        return dynamoDbClientEnhancedClient
            .table(tableName, TableSchema.fromBean(RailRoadCarEntity::class.java))
    }
    fun createNewTable(): String? {
        val tableName = "RailRoadCar"
        val  attDefId = AttributeDefinition.builder()
            .attributeName("id")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefName = AttributeDefinition.builder()
            .attributeName("name")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefDestination = AttributeDefinition.builder()
            .attributeName("destination")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefReceiver = AttributeDefinition.builder()
            .attributeName("receiver")
            .attributeType(ScalarAttributeType.S).build()
        var attributeDefinitionList= mutableListOf<AttributeDefinition>(attDefId,
                         attDefName, attDefDestination, attDefReceiver)
        val keySchemaName =  KeySchemaElement.builder()
            .attributeName("id")
            .keyType(KeyType.HASH)
            .build()
       val keySchemaDestination =  KeySchemaElement.builder()
            .attributeName("name")
            .keyType(KeyType.RANGE)
            .build()
        var keySchemaList= mutableListOf<KeySchemaElement>(keySchemaName, keySchemaDestination)
        val provisionedVal =  ProvisionedThroughput.builder()
            .readCapacityUnits(10)
            .writeCapacityUnits(10)
            .build()
        var keySecondarySchemaId = KeySchemaElement.builder()
            .attributeName("id")
            .keyType(KeyType.HASH)
            .build()
        var keySecondarySchemaReceiver = KeySchemaElement.builder()
            .attributeName("receiver")
            .keyType(KeyType.RANGE)
            .build()
        var keySecondarySchemaDestination = KeySchemaElement.builder()
            .attributeName("destination")
            .keyType(KeyType.RANGE)
            .build()
        val keySecondarySchemaReceiverList = mutableListOf<KeySchemaElement>(keySecondarySchemaId,
            keySecondarySchemaReceiver)
        val secondaryReceiverIndex = LocalSecondaryIndex.builder()
            .indexName("secondaryReceiverIdx")
            .keySchema(keySecondarySchemaReceiverList)
            .projection(Projection.builder()
                .projectionType(ProjectionType.ALL)
                .build())
            .build()
        val keySecondarySchemaDestinationList = mutableListOf<KeySchemaElement>(keySecondarySchemaId,
            keySecondarySchemaDestination)
        val secondaryDestinationIndex = LocalSecondaryIndex.builder()
            .indexName("secondaryDestinationIdx")
            .keySchema(keySecondarySchemaDestinationList)
            .projection(Projection.builder()
                .projectionType(ProjectionType.ALL)
                .build())
            .build()

        val localSecondaryIndexList = mutableListOf<LocalSecondaryIndex>(secondaryReceiverIndex, secondaryDestinationIndex)
        val request = CreateTableRequest.builder()
            .keySchema(keySchemaList)
            .attributeDefinitions(attributeDefinitionList)
            .localSecondaryIndexes(localSecondaryIndexList)
            .provisionedThroughput(provisionedVal)
            .tableName(tableName)
            .build()
        return PersistenceHelper.createTable(tableName,PersistenceHelper.dynamoDbClient(), request)
    }
}