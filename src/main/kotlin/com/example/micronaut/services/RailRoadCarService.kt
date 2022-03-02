package com.example.micronaut.services

import com.example.micronaut.entities.RailRoadCarEntity
import com.example.micronaut.entities.RailRoadCarEntity.Companion.toRailRoadCar
import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.models.RailRoadCar.Companion.toRailRoadCarEntity
import com.example.micronaut.repositories.RailRoadCarRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

@Singleton
class RailRoadCarService {
    @Inject
    lateinit var railRoadCarRepository: RailRoadCarRepository

    fun findByName(name: String): Optional<RailRoadCar> {
        val result =  railRoadCarRepository.findByName(name)
        if(result.isPresent) {
            return Optional.of(result.get().toRailRoadCar())
        } else {
            return Optional.empty()
        }
    }

    fun delete(name: String) {
        railRoadCarRepository.delete(RailRoadCarEntity(name, null, null))
    }

    fun save(entity: RailRoadCar): RailRoadCar {
        return railRoadCarRepository.save(entity.toRailRoadCarEntity()).toRailRoadCar()
    }

    fun findAll(): List<RailRoadCar> {
        return railRoadCarRepository.findAll().map { t -> t.toRailRoadCar() }
    }





}