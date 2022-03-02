package com.example.micronaut.entities

import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.models.RailRoadCarDestination
import com.example.micronaut.models.Receiver
import io.micronaut.core.annotation.Introspected
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@Introspected
@DynamoDbBean
data class RailRoadCarEntity(
    @get: DynamoDbPartitionKey
    var name: String? = null,
    var destination: String? = null,
    var receiver: String? = null
) {
    companion object {
        fun RailRoadCarEntity.toRailRoadCar() = RailRoadCar(
            name = name,
            destination = destination,
            receiver = receiver
        )
    }
}
