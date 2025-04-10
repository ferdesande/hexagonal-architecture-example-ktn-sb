package com.fdesande.hexarch.infrastructure.mapper

import com.fdesande.hexarch.domain.model.Image
import com.fdesande.hexarch.domain.model.Price
import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.infrastructure.api.dto.ImageDto
import com.fdesande.hexarch.infrastructure.api.mapper.ProductMapper
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class ProductMapperTest {

    private val sampleUUID = UUID.randomUUID()
    private val productId = ProductId(sampleUUID)
    private val samplePrice = Price(BigDecimal.valueOf(99.99))
    private val image1 = Image(UUID.randomUUID(), "https://example.com/image1.jpg", "main")
    private val image2 = Image(UUID.randomUUID(), "https://example.com/image2.jpg", "thumbnail")
    private val now = LocalDateTime.now()
    private val sampleProduct = Product(
        id = productId,
        name = "Test Product",
        description = "This is a test product",
        brand = "Test Brand",
        price = samplePrice,
        imageUrls = listOf(image1, image2),
        createdAt = now,
        updatedAt = now
    )

    private val productMapper = ProductMapper()

    @Test
    fun `toDto maps product correctly`() {
        // When
        val actual = productMapper.toDto(sampleProduct)

        // Then
        assertThat(actual, notNullValue())
        assertThat(actual!!.id, equalTo(sampleUUID))
        assertThat(actual.name, equalTo(sampleProduct.name))
        assertThat(actual.description, equalTo(sampleProduct.description))
        assertThat(actual.brand, equalTo(sampleProduct.brand))

        assertThat(actual.price.amount, equalTo(sampleProduct.price.amount))

        assertThat(
            actual.imageUrls,
            contains(ImageDto(image1.url, image1.name), ImageDto(image2.url, image2.name))
        )
    }

    @Test
    fun `toDto returns null when product is null`() {
        // When
        val actual = productMapper.toDto(null)

        // Then
        assertThat(actual, nullValue())
    }

    @Test
    fun `uuidToProductId returns ProductId`() {
        // When
        val actual = productMapper.uuidToProductId(sampleUUID)

        // Then
        assertThat(actual, equalTo(ProductId(sampleUUID)))
    }
}
