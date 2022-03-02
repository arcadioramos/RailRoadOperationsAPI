package com.example.micronaut.controllers

import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.models.RailRoadCarDestination
import com.example.micronaut.services.RailRoadCarDestinationService
import com.example.micronaut.services.RailRoadCarService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import java.util.*
import javax.validation.Valid

@Controller("/railRoadCarDestinations")
open class RailRoadCarsDestinationController {
    @Inject
    lateinit var railRoadCarDestinationService: RailRoadCarDestinationService

    @Get("/{id}")
    fun getRailRoadCarDestination(id: String): Optional<RailRoadCarDestination> =
        railRoadCarDestinationService.findById(id)

    @Put
    open fun update(@Body @Valid railRoadCarDestination: RailRoadCarDestination): HttpResponse<RailRoadCarDestination> {
        return HttpResponse.ok( railRoadCarDestinationService.save(railRoadCarDestination))
    }

    @Get("/list")
    fun railRoadCarDestinationList(): List<RailRoadCarDestination> =
        railRoadCarDestinationService.findAll()

    @Post
    open fun save(@Body("railRoadCarDestination") @Valid railRoadCarDestination: RailRoadCarDestination) : HttpResponse<RailRoadCarDestination> {
        return HttpResponse.created( railRoadCarDestinationService.save(railRoadCarDestination))
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: String) = railRoadCarDestinationService.delete(id)

}