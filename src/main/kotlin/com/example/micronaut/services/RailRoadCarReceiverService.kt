package com.example.micronaut.services

import com.example.micronaut.entities.RailRoadCarReceiverEntity
import com.example.micronaut.entities.RailRoadCarReceiverEntity.Companion.toRailRoadCarReceiver
import com.example.micronaut.models.RailRoadCarReceiver
import com.example.micronaut.models.RailRoadCarReceiver.Companion.toRailRoadCarReceiverEntity
import com.example.micronaut.repositories.RailRoadCarReceiverRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

@Singleton
class RailRoadCarReceiverService {
    @Inject
    lateinit var railRoadCarReceiverRepository: RailRoadCarReceiverRepository

    fun findById(id: String): Optional<RailRoadCarReceiver> {
        val result =  railRoadCarReceiverRepository.findById(id)
        if(result.isPresent) {
            return Optional.of(result.get().toRailRoadCarReceiver())
        } else {
            return Optional.empty()
        }
    }

    fun delete(id: String) {
        railRoadCarReceiverRepository.delete(id)
    }

    fun save(entity: RailRoadCarReceiver): RailRoadCarReceiver {
        entity.id = UUID.randomUUID().toString()
        return railRoadCarReceiverRepository.save(entity.toRailRoadCarReceiverEntity()).toRailRoadCarReceiver()
    }

    fun update(entity: RailRoadCarReceiver): RailRoadCarReceiver {
        return railRoadCarReceiverRepository.update(entity.toRailRoadCarReceiverEntity()).toRailRoadCarReceiver()
    }

    fun findAll(): List<RailRoadCarReceiver> {
        return railRoadCarReceiverRepository.findAllByType("Receiver", RailRoadCarReceiverEntity::class.java).map { t -> t.toRailRoadCarReceiver() }
    }
}