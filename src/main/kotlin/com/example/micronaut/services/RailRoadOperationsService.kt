package com.example.micronaut.services

import com.example.micronaut.entities.RailRoadCarDestinationEntity
import com.example.micronaut.entities.RailRoadCarDestinationEntity.Companion.toRailRoadCarDestination
import com.example.micronaut.entities.RailRoadCarEntity.Companion.toRailRoadCar
import com.example.micronaut.entities.RailRoadCarReceiverEntity
import com.example.micronaut.entities.RailRoadCarReceiverEntity.Companion.toRailRoadCarReceiver
import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.repositories.RailRoadCarDestinationRepository
import com.example.micronaut.repositories.RailRoadCarReceiverRepository
import com.example.micronaut.repositories.RailRoadCarRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

@Singleton
class RailRoadOperationsService {
    @Inject
    lateinit var railRoadCarDestinationRepository: RailRoadCarDestinationRepository

    @Inject
    lateinit var railRoadCarReceiverRepository: RailRoadCarReceiverRepository

    @Inject
    lateinit var railRoadCarRepository: RailRoadCarRepository

    fun getDepartureList():List<RailRoadCar>{
        val unsortedList = railRoadCarRepository.findAll().map { t -> t.toRailRoadCar() }
        val destinationMap = railRoadCarDestinationRepository
            .findAllByType("Destination", RailRoadCarDestinationEntity::class.java)
            .map { t -> t.toRailRoadCarDestination() }.associateBy({it.key}, {it.value}).toMap()
        val receiverMap = railRoadCarReceiverRepository
            .findAllByType("Receiver", RailRoadCarReceiverEntity::class.java)
            .map { t -> t.toRailRoadCarReceiver() }.associateBy({it.key}, {it.value}).toMap()
        return unsortedList.sortedWith(compareBy({destinationMap.get(it.destination)}, {receiverMap.get(it.receiver)}))
    }
}
