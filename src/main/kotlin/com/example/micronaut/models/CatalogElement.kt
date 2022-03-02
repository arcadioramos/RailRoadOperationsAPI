package com.example.micronaut.models

import javax.validation.constraints.NotBlank

open class CatalogElement(
    @field:NotBlank open var key:  String? = null,
    @field:NotBlank open var value: Int? = 0,
    @field:NotBlank open var type: String? = null) {
}