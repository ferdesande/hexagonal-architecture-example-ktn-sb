package com.fdesande.hexarch.infrastructure.api.mapper

import com.fdesande.hexarch.domain.model.Image
import com.fdesande.hexarch.domain.model.Price
import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.infrastructure.api.dto.ImageDto
import com.fdesande.hexarch.infrastructure.api.dto.PriceDto
import com.fdesande.hexarch.infrastructure.api.dto.ProductDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProductMapper {
    fun toDto(product: Product?): ProductDto? =
        product?.let {
            ProductDto(
                id = it.id.id,
                name = it.name,
                description = it.description,
                brand = it.brand,
                price = toDto(it.price),
                imageUrls = it.imageUrls.map { img -> toDto(img) }
            )
        }

    fun uuidToProductId(id: UUID): ProductId = ProductId(id)

    private fun toDto(price: Price): PriceDto = PriceDto(price.amount)
    private fun toDto(image: Image): ImageDto =
        ImageDto(
            url = image.url,
            name = image.name
        )
}
