package com.example.micronaut.controllers

import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.models.Receiver
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.validation.Valid

@MicronautTest
class RailroadOperationsControllerTest(private val embeddedServer: EmbeddedServer) {


    @Client("/railroadoperations")
    interface RailRoadOperationsClient {
        @Get(uri="/departList")
        fun getDepartList(): HttpResponse<List<RailRoadCar>>
    }

    @Client("/railRoadCars")
    interface RailRoadCarsClient {
        @Post
        open fun save(@Body @Valid railRoadCar: RailRoadCar) : HttpResponse<RailRoadCar>
    }

    @Inject
    lateinit var railRoadOperationsClient:RailRoadOperationsClient

    @Inject
    lateinit var railRoadCarsClient:RailRoadCarsClient

    @Test
    fun testServerIsRunning() {
        assert(embeddedServer.isRunning())
    }

    @Test
    fun getDepartListTest() {
        val car1: RailRoadCar = RailRoadCar("Box Car 1", "Houston", "FedEx")
        railRoadCarsClient.save(car1)
        val car2: RailRoadCar = RailRoadCar("Box Car 2", "Chicago", "FedEx")
        railRoadCarsClient.save(car2)
        val car3: RailRoadCar = RailRoadCar("Box Car 3", "Houston", "UPS")
        railRoadCarsClient.save(car3)
        val car4: RailRoadCar = RailRoadCar("Box Car 4", "LA", "Old Dominion")
        railRoadCarsClient.save(car4)
        val car5: RailRoadCar = RailRoadCar("Box Car 5", "LA", "FedEx")
        railRoadCarsClient.save(car5)
        val car6: RailRoadCar = RailRoadCar("Box Car 6", "Houston", "Old Dominion")
        railRoadCarsClient.save(car6)



        val departList = railRoadOperationsClient.getDepartList()
        assertEquals(departList.code(), HttpStatus.OK.code)
        assertEquals(departList.body()?.toList()?.size,6)
        assertEquals(departList.body()?.toList()?.get(0), car3)
        assertEquals(departList.body()?.toList()?.get(1), car1)
        assertEquals(departList.body()?.toList()?.get(2), car6)
        assertEquals(departList.body()?.toList()?.get(3), car2)
        assertEquals(departList.body()?.toList()?.get(4), car5)
        assertEquals(departList.body()?.toList()?.get(5), car4)
    }
}
