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
        val car1: RailRoadCar = RailRoadCar(name="Box Car 1", destination = "Houston", receiver = "FedEx")
        railRoadCarsClient.save(car1)
        val car2: RailRoadCar = RailRoadCar(name="Box Car 2", destination = "Chicago", receiver = "FedEx")
        railRoadCarsClient.save(car2)
        val car3: RailRoadCar = RailRoadCar(name="Box Car 3", destination = "Houston", receiver = "UPS")
        railRoadCarsClient.save(car3)
        val car4: RailRoadCar = RailRoadCar(name="Box Car 4", destination = "LA", receiver = "Old Dominion")
        railRoadCarsClient.save(car4)
        val car5: RailRoadCar = RailRoadCar(name="Box Car 5", destination = "LA", receiver = "FedEx")
        railRoadCarsClient.save(car5)
        val car6: RailRoadCar = RailRoadCar(name="Box Car 6", destination = "Houston", receiver = "Old Dominion")
        railRoadCarsClient.save(car6)



        val departList = railRoadOperationsClient.getDepartList()
        assertEquals(departList.code(), HttpStatus.OK.code)
        assertEquals(departList.body()?.toList()?.size,6)
        assertEquals(departList.body()?.toList()?.get(0)?.name, car3.name)
        assertEquals(departList.body()?.toList()?.get(1)?.name, car1.name)
        assertEquals(departList.body()?.toList()?.get(2)?.name, car6.name)
        assertEquals(departList.body()?.toList()?.get(3)?.name, car2.name)
        assertEquals(departList.body()?.toList()?.get(4)?.name, car5.name)
        assertEquals(departList.body()?.toList()?.get(5)?.name, car4.name)
    }
}
