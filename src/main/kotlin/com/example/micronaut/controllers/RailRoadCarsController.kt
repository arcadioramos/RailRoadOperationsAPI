package com.example.micronaut.controllers

import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.services.RailRoadCarService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import java.util.*
import javax.validation.Valid

@Controller("/railRoadCars")
open class RailRoadCarsController {
    @Inject
    lateinit var railRoadCarService: RailRoadCarService

    @Get("/{id}")
    fun getRailRoadCar(id: String): Optional<RailRoadCar> =
        railRoadCarService.findById(id)

    @Put()
    open fun update(@Body @Valid railRoadCar: RailRoadCar): HttpResponse<RailRoadCar> {
        return HttpResponse.ok( railRoadCarService.update(railRoadCar))
    }

    @Get("/list")
    fun railRoadCarList(): List<RailRoadCar> =
        railRoadCarService.findAll()

    @Post
    open fun save(@Body @Valid railRoadCar: RailRoadCar) : HttpResponse<RailRoadCar> {
        return HttpResponse.created( railRoadCarService.save(railRoadCar))
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: String) = railRoadCarService.delete(id)

}