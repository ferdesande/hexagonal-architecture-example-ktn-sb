package com.fdesande.hexarch.domain.model

import java.time.LocalDateTime

data class Product(
    val id: ProductId,
    val name: String,
    val description: String,
    val brand: String,
    val price: Price,
    val imageUrls: List<Image>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(name.isNotBlank()) { "Product name cannot be blank" }
        require(brand.isNotBlank()) { "Brand cannot be blank" }
    }
}
