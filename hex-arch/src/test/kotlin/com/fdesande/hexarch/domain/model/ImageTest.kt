package com.fdesande.hexarch.domain.model

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*
import kotlin.test.Test

class ImageTest {
    @Test
    fun `image is created correctly`() {
        // Act
        val product = assertDoesNotThrow {
            createImage("https://some.url")
        }

        // Assert
        assertThat(product.url, equalTo("https://some.url"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["     ", ""])
    fun `image throws IllegalArgumentException when url is blank`(url: String) {
        // Act / Assert
        val exception = assertThrows<IllegalArgumentException> {
            createImage(url)
        }

        assertThat(exception.message, equalTo("Image URL cannot be blank"))
    }

    private fun createImage(url: String) = Image(
        id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
        url = url,
        name = "the image name"
    )
}
