package com.example.micronaut.repositories

import com.example.micronaut.helpers.PersistenceHelper
import com.example.micronaut.models.CatalogElement
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.*

@Singleton
open class RailRoadCarCatalogRepository() {
    fun createNewTable(): String? {
        val tableName = "RailRoadCarCatalog"
        val  attDefId = AttributeDefinition.builder()
            .attributeName("id")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefKey = AttributeDefinition.builder()
            .attributeName("key")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefValue = AttributeDefinition.builder()
            .attributeName("value")
            .attributeType(ScalarAttributeType.N).build()
        val  attDefType = AttributeDefinition.builder()
            .attributeName("type")
            .attributeType(ScalarAttributeType.S).build()
        var attributeDefinitionList= mutableListOf<AttributeDefinition>(attDefId,
            attDefKey, attDefValue, attDefType)



        val keySchemaId =  KeySchemaElement.builder()
            .attributeName("id")
            .keyType(KeyType.HASH)
            .build()
        val keySchemaKey =  KeySchemaElement.builder()
            .attributeName("key")
            .keyType(KeyType.RANGE)
            .build()
        var keySchemaList= mutableListOf<KeySchemaElement>(keySchemaId, keySchemaKey)


        val provisionedVal =  ProvisionedThroughput.builder()
            .readCapacityUnits(10)
            .writeCapacityUnits(10)
            .build()


        var keySecondarySchemaId = KeySchemaElement.builder()
            .attributeName("id")
            .keyType(KeyType.HASH)
            .build()
        var keySecondarySchemaType = KeySchemaElement.builder()
            .attributeName("type")
            .keyType(KeyType.RANGE)
            .build()
        var keySecondarySchemaValue = KeySchemaElement.builder()
            .attributeName("value")
            .keyType(KeyType.RANGE)
            .build()

        val keySecondarySchemaTypeList = mutableListOf<KeySchemaElement>(keySecondarySchemaId, keySecondarySchemaType)
        val secondaryIndexType = LocalSecondaryIndex.builder()
            .indexName("secondaryIdxType")
            .keySchema(keySecondarySchemaTypeList)
            .projection(Projection.builder()
                .projectionType(ProjectionType.ALL)
                .build())
            .build()

        val keySecondarySchemaValueList = mutableListOf<KeySchemaElement>(keySecondarySchemaId, keySecondarySchemaValue)
        val secondaryIndexValue = LocalSecondaryIndex.builder()
            .indexName("secondaryIdxValue")
            .keySchema(keySecondarySchemaValueList)
            .projection(Projection.builder()
                .projectionType(ProjectionType.ALL)
                .build())
            .build()

        val localSecondaryIndexList = mutableListOf<LocalSecondaryIndex>(secondaryIndexType,secondaryIndexValue)


        val request = CreateTableRequest.builder()
            .keySchema(keySchemaList)
            .attributeDefinitions(attributeDefinitionList)
            .localSecondaryIndexes(localSecondaryIndexList)
            .provisionedThroughput(provisionedVal)
            .tableName(tableName)
            .build()

        return PersistenceHelper.createTable(tableName, PersistenceHelper.dynamoDbClient(), request)
    }

    fun <T: CatalogElement> findAllById(id: String, entity: Class<T>): Optional<T> {
        val railRoadCarDestinationTable: DynamoDbTable<T> = dynamoDbTable(entity)
        return railRoadCarDestinationTable.scan().items().stream().filter { x -> x.id.equals(id) }.findFirst()
    }

    fun <T: CatalogElement> findAllByType(type: String, entity: Class<T>): MutableIterable<T> {
        val railRoadCarDestinationTable: DynamoDbTable<T> = dynamoDbTable(entity)
        return railRoadCarDestinationTable.scan().items().stream().filter { x -> x.type.equals(type) }.toList()
    }

    fun <T> dynamoDbTable(entity: Class<T>): DynamoDbTable<T> {
        val tableName = "RailRoadCarCatalog"
        val dynamoDbClientEnhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(PersistenceHelper.dynamoDbClient())
            .build()

        return dynamoDbClientEnhancedClient
            .table(tableName, TableSchema.fromBean(entity))
    }

}