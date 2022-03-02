package com.example.micronaut.services

import com.example.micronaut.entities.RailRoadCarDestinationEntity
import com.example.micronaut.entities.RailRoadCarDestinationEntity.Companion.toRailRoadCarDestination
import com.example.micronaut.models.RailRoadCarDestination
import com.example.micronaut.models.RailRoadCarDestination.Companion.toRailRoadCarDestinationEntity
import com.example.micronaut.repositories.RailRoadCarDestinationRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

@Singleton
class RailRoadCarDestinationService {
    @Inject
    lateinit var railRoadCarDestinationRepository: RailRoadCarDestinationRepository

    fun findById(id: String): Optional<RailRoadCarDestination> {
        val result =  railRoadCarDestinationRepository.findById(id)
        if(result.isPresent) {
            return Optional.of(result.get().toRailRoadCarDestination())
        } else {
            return Optional.empty()
        }
    }

    fun delete(id: String) {
        railRoadCarDestinationRepository.delete(RailRoadCarDestinationEntity(id, 0))
    }

    fun save(entity: RailRoadCarDestination): RailRoadCarDestination {
        return railRoadCarDestinationRepository.save(entity.toRailRoadCarDestinationEntity()).toRailRoadCarDestination()
    }

    fun findAll(): List<RailRoadCarDestination> {
        return railRoadCarDestinationRepository.findAllByType("Destination", RailRoadCarDestinationEntity::class.java).map { t -> t.toRailRoadCarDestination() }
    }
}