package com.fdesande.hexarch.domain.model

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class PriceTest {
    @ParameterizedTest
    @ValueSource(doubles = [0.00, 1.23, 1000.32])
    fun `price is created correctly`(amount: Double) {
        // Act
        val price = assertDoesNotThrow {
            Price(BigDecimal.valueOf(amount))
        }

        // Assert
        assertThat(price.amount.toPlainString(), equalTo(amount.toString()))
    }

    @ParameterizedTest
    @ValueSource(doubles = [-0.01, -12.32])
    fun `price throws IllegalArgumentException when value is negative`(amount: Double) {
        // Act / Assert
        val exception = assertThrows<IllegalArgumentException> {
            Price(BigDecimal.valueOf(amount))
        }

        assertThat(exception.message, equalTo("Price cannot be negative"))
    }
}
