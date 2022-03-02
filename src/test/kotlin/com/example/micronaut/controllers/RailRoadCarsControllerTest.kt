package com.example.micronaut.controllers

import com.example.micronaut.models.RailRoadCar
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
class RailRoadCarsControllerTest(private val embeddedServer: EmbeddedServer) {

    @Client("/railRoadCars")
    interface RailRoadCarsClient {
        @Get("/list")
        fun railRoadCarList(): List<RailRoadCar>
    }

    @Inject
    lateinit var railRoadCarsClient:RailRoadCarsClient

    @Test
    fun testServerIsRunning() {
        assert(embeddedServer.isRunning())
    }

    @Test
    fun getListAll() {
        val departList = railRoadCarsClient.railRoadCarList();
        println(departList)
    }
}
