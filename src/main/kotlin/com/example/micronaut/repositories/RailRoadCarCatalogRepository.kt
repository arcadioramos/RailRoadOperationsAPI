package com.example.micronaut.repositories

import com.example.micronaut.entities.RailRoadCarDestinationEntity
import com.example.micronaut.helpers.PersistenceHelper
import com.example.micronaut.models.CatalogElement
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.internal.conditional.EqualToConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.HashMap
import javax.xml.catalog.CatalogException

@Singleton
open class RailRoadCarCatalogRepository() {
    fun createNewTable(): String? {
        val tableName = "RailRoadCarCatalog"
        val  attDefKey = AttributeDefinition.builder()
            .attributeName("key")
            .attributeType(ScalarAttributeType.S).build()
        val  attDefValue = AttributeDefinition.builder()
            .attributeName("value")
            .attributeType(ScalarAttributeType.N).build()
        val  attDefType = AttributeDefinition.builder()
            .attributeName("type")
            .attributeType(ScalarAttributeType.S).build()
        var attributeDefinitionList= mutableListOf<AttributeDefinition>(attDefKey, attDefValue, attDefType)



        val keySchemaKeyVal =  KeySchemaElement.builder()
            .attributeName("key")
            .keyType(KeyType.HASH)
            .build()
        val keySchemaValueVal =  KeySchemaElement.builder()
            .attributeName("value")
            .keyType(KeyType.RANGE)
            .build()
        var keySchemaValList= mutableListOf<KeySchemaElement>(keySchemaKeyVal, keySchemaValueVal)


        val provisionedVal =  ProvisionedThroughput.builder()
            .readCapacityUnits(10)
            .writeCapacityUnits(10)
            .build()


        var keySchemaName = KeySchemaElement.builder()
            .attributeName("key")
            .keyType(KeyType.HASH)
            .build()

        var keySchemaType = KeySchemaElement.builder()
            .attributeName("type")
            .keyType(KeyType.RANGE)
            .build()

        val keySchemaList = mutableListOf<KeySchemaElement>(keySchemaName, keySchemaType)
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

        return PersistenceHelper.createTable(tableName, PersistenceHelper.dynamoDbClient(), request)
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