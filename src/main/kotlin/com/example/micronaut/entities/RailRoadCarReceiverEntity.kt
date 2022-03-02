package com.example.micronaut.entities

import com.example.micronaut.models.CatalogElement
import com.example.micronaut.models.RailRoadCarReceiver
import io.micronaut.core.annotation.Introspected
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import javax.validation.constraints.NotBlank

@Introspected
@DynamoDbBean
data class RailRoadCarReceiverEntity(
    @get: DynamoDbPartitionKey
    @field:NotBlank override var key: String? =  null,
    @field:NotBlank override var value: Int? = 0,
    @field:NotBlank override var type: String? = "Receiver"): CatalogElement(){
    companion object {
        fun RailRoadCarReceiverEntity.toRailRoadCarReceiver() = RailRoadCarReceiver(
            key = key,
            value = value
        )
    }
}