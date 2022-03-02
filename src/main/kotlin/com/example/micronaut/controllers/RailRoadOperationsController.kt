package com.example.micronaut.controllers

import com.example.micronaut.models.RailRoadCar
import com.example.micronaut.services.RailRoadOperationsService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import jakarta.inject.Inject

@Controller("/railroadoperations")
class RailRoadOperationsController {

    @Inject
    lateinit var railRoadOperationsService: RailRoadOperationsService

    @Get(uri="/departList")
    fun getDepartList(): HttpResponse<List<RailRoadCar>> {
        return HttpResponse.ok<List<RailRoadCar>>().body(railRoadOperationsService.getDepartureList());
    }
}