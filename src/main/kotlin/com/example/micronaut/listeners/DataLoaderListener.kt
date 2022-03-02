package com.example.micronaut.listeners

import com.example.micronaut.models.RailRoadCarReceiver
import com.example.micronaut.repositories.RailRoadCarCatalogRepository
import com.example.micronaut.repositories.RailRoadCarDestinationRepository
import com.example.micronaut.repositories.RailRoadCarReceiverRepository
import com.example.micronaut.repositories.RailRoadCarRepository
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import jakarta.inject.Inject
import jakarta.inject.Singleton


@Singleton
@Requires(notEnv = [Environment.TEST])
class DataLoaderListener: ApplicationEventListener<ServerStartupEvent> {
    @Inject
    lateinit var railRoadCarRepository: RailRoadCarRepository

    @Inject
    lateinit var railRoadCarCatalogRepository: RailRoadCarCatalogRepository

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        railRoadCarRepository.createNewTable()
        railRoadCarCatalogRepository.createNewTable()
        println("table creation process is finished")
    }
}