package com.fdesande.hexarch.infrastructure.output.persistence.mapper

import com.fdesande.hexarch.domain.model.Image
import com.fdesande.hexarch.domain.model.Price
import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.infrastructure.output.persistence.entitiy.ProductEntity
import com.fdesande.hexarch.infrastructure.output.persistence.entitiy.ProductImageEntity
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class ProductEntityMapper {
    fun toProduct(
        entity: ProductEntity
    ): Product = Product(
        id = ProductId(entity.id!!),
        name = entity.name,
        description = entity.description,
        brand = entity.brand.name,
        price = Price(entity.price!!.amount),
        imageUrls = entity.imageUrls.map { toImage(it) },
        createdAt = toLocalDateTime(entity.createdAt),
        updatedAt = toLocalDateTime(entity.updatedAt)
    )

    private fun toImage(
        imageEntity: ProductImageEntity
    ): Image = Image(
        id = imageEntity.id!!,
        url = imageEntity.url,
        name = imageEntity.name,
    )

    private fun toLocalDateTime(
        instant: Instant?
    ): LocalDateTime = instant
        ?.let { LocalDateTime.ofInstant(instant, ZoneId.systemDefault()) }
        ?: LocalDateTime.now(ZoneId.systemDefault())
}
