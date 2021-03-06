package com.example.micronaut.models

import com.example.micronaut.entities.RailRoadCarDestinationEntity
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class RailRoadCarDestination(
    override var id: String? = null,
    @field:NotBlank override var key:  String? = null,
    @field:NotBlank override var value: Int? = 0,
    @field:NotBlank override var type: String? = "Destination"):CatalogElement(){
    companion object {
        fun RailRoadCarDestination.toRailRoadCarDestinationEntity() = RailRoadCarDestinationEntity(
            id = id,
            key = key,
            value = value,
            type = type
        )
    }
}