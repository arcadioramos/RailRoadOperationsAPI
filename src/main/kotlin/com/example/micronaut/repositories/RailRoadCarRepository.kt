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

    fun findByName(name: String): Optional<RailRoadCarEntity> {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        return Optional.of(railRoadCarTable.getItem(Key.builder().partitionValue(AttributeValue.builder().s(name).build()).build()))
    }

     fun findAll(): MutableIterable<RailRoadCarEntity> {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        return railRoadCarTable.scan().items()
    }

    fun delete(entity: RailRoadCarEntity) {
        val railRoadCarTable: DynamoDbTable<RailRoadCarEntity> = dynamoDbTable()
        railRoadCarTable.deleteItem(entity)
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
        val  attDefName = AttributeDefinition.builder()
            .attributeName("name")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefDestination = AttributeDefinition.builder()
            .attributeName("destination")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefReceiver = AttributeDefinition.builder()
            .attributeName("receiver")
            .attributeType(ScalarAttributeType.S).build()
        var attributeDefinitionList= mutableListOf<AttributeDefinition>(attDefName, attDefDestination, attDefReceiver)



        val keySchemaNameVal =  KeySchemaElement.builder()
            .attributeName("name")
            .keyType(KeyType.HASH)
            .build()
        val keySchemaDestinationVal =  KeySchemaElement.builder()
            .attributeName("destination")
            .keyType(KeyType.RANGE)
            .build()
        var keySchemaValList= mutableListOf<KeySchemaElement>(keySchemaNameVal, keySchemaDestinationVal)


        val provisionedVal =  ProvisionedThroughput.builder()
            .readCapacityUnits(10)
            .writeCapacityUnits(10)
            .build()

        var keySchemaName = KeySchemaElement.builder()
            .attributeName("name")
            .keyType(KeyType.HASH)
            .build()

        var keySchemaReceiver = KeySchemaElement.builder()
            .attributeName("receiver")
            .keyType(KeyType.RANGE)
            .build()

        val keySchemaList = mutableListOf<KeySchemaElement>(keySchemaName, keySchemaReceiver)
        val secondaryIndex = LocalSecondaryIndex.builder()
            .indexName("secondaryIdx")
            .keySchema(keySchemaList)
            .projection(Projection.builder()
                .projectionType(ProjectionType.ALL)
                .build())
            .build()

        val localSecondaryIndexList = mutableListOf<LocalSecondaryIndex>(secondaryIndex)

        val request = CreateTableRequest.builder()
            .keySchema(keySchemaValList)
            .attributeDefinitions(attributeDefinitionList)
            .localSecondaryIndexes(localSecondaryIndexList)
            .provisionedThroughput(provisionedVal)
            .tableName(tableName)
            .build()


        return PersistenceHelper.createTable(tableName,PersistenceHelper.dynamoDbClient(), request)
    }

}