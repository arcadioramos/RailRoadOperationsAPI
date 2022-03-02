package com.example.micronaut.controllers

import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.models.RailRoadCarReceiver
import com.example.micronaut.services.RailRoadCarReceiverService
import com.example.micronaut.services.RailRoadCarService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import java.util.*
import javax.validation.Valid

@Controller("/railRoadCarReceivers")
open class RailRoadCarsReceiverController {
    @Inject
    lateinit var railRoadCarReceiverService: RailRoadCarReceiverService

    @Get("/{id}")
    fun getRailRoadCarReceiver(id: String): Optional<RailRoadCarReceiver> =
        railRoadCarReceiverService.findById(id)

    @Put
    open fun update(@Body @Valid railRoadCarReceiver: RailRoadCarReceiver): HttpResponse<RailRoadCarReceiver> {
        return HttpResponse.ok( railRoadCarReceiverService.save(railRoadCarReceiver))
    }

    @Get("/list")
    fun railRoadCarReceiverList(): List<RailRoadCarReceiver> =
        railRoadCarReceiverService.findAll()

    @Post
    open fun save(@Body("railRoadCarReceiver") @Valid railRoadCarReceiver: RailRoadCarReceiver) : HttpResponse<RailRoadCarReceiver> {
        return HttpResponse.created( railRoadCarReceiverService.save(railRoadCarReceiver))
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: String) = railRoadCarReceiverService.delete(id)

}