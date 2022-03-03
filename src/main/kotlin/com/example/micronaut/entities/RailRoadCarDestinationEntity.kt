package com.example.micronaut.entities

import com.example.micronaut.models.CatalogElement
import com.example.micronaut.models.RailRoadCarDestination
import io.micronaut.core.annotation.Introspected
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey
import javax.validation.constraints.NotBlank

@Introspected
@DynamoDbBean
data class RailRoadCarDestinationEntity(
    @get: DynamoDbPartitionKey
    @field:NotBlank override var id: String? =  null,
    @get: DynamoDbSortKey
    @field:NotBlank override var key: String? =  null,
    @field:NotBlank override var value: Int? = 0,
    @field:NotBlank override var type: String? = "Destination"): CatalogElement(){
    companion object {
        fun RailRoadCarDestinationEntity.toRailRoadCarDestination() = RailRoadCarDestination(
            id = id,
            key = key,
            value = value,
            type = type
        )
    }
}