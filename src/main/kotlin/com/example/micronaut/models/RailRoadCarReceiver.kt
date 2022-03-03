package com.example.micronaut.models

import com.example.micronaut.entities.RailRoadCarReceiverEntity
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class RailRoadCarReceiver(
    override var id: String? = null,
    @field:NotBlank override var key:  String? = null,
    @field:NotBlank override var value: Int? = 0,
    @field:NotBlank override var type: String? = "Receiver"):CatalogElement(){
    companion object {
        fun RailRoadCarReceiver.toRailRoadCarReceiverEntity() = RailRoadCarReceiverEntity(
            id = id,
            key = key,
            value = value,
            type = type
        )
    }
}