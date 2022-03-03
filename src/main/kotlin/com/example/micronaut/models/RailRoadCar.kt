package com.example.micronaut.models

import com.example.micronaut.entities.RailRoadCarEntity
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

enum class Receiver {`UPS`,`FedEx`, `Old Dominion`}

@Introspected
data class RailRoadCar(
    var id: String? = null,
    @field:NotBlank var name: String? = null,
    @field:NotBlank var destination: String? = null,
    @field:NotBlank var receiver: String? = null
) {
    companion object {
        fun RailRoadCar.toRailRoadCarEntity() = RailRoadCarEntity(
            id = id,
            name = name,
            destination = destination,
            receiver = receiver
        )
    }
}
