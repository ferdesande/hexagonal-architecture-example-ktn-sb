package com.fdesande.hexarch.infrastructure.input.api.dto

import java.util.*

data class ProductDto(
    val id: UUID,
    val name: String,
    val description: String,
    val brand: String,
    val price: PriceDto,
    val imageUrls: List<ImageDto>
)
