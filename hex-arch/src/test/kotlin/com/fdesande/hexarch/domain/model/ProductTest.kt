package com.fdesande.hexarch.domain.model

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

class ProductTest {

    @Test
    fun `product is created correctly`() {
        // When
        val product = assertDoesNotThrow {
            createProduct(name = "foo", brand = "bar")
        }

        // Then
        assertThat(product.name, equalTo("foo"))
        assertThat(product.brand, equalTo("bar"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["     ", ""])
    fun `product throws IllegalArgumentException when name is blank`(name: String) {
        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            createProduct(name = name)
        }

        assertThat(exception.message, equalTo("Product name cannot be blank"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["     ", ""])
    fun `product throws IllegalArgumentException when brand is blank`(brand: String) {
        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            createProduct(brand = brand)
        }

        assertThat(exception.message, equalTo("Brand cannot be blank"))
    }

    private fun createProduct(
        name: String = "sample name",
        brand: String = "sample brande"
    ) = Product(
        id = ProductId(UUID.fromString("00000000-0000-0000-0000-000000000001")),
        name = name,
        description = "sample description",
        brand = brand,
        price = Price(BigDecimal.valueOf(100.00)),
        imageUrls = emptyList(),
    )
}
