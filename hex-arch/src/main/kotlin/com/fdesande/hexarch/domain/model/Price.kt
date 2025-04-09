package com.fdesande.hexarch.domain.model

import java.math.BigDecimal

// Hint: In the future, a Currency could be added if there is a need for it
data class Price(val amount: BigDecimal) {
    init {
        require(amount >= BigDecimal.ZERO) { "Price cannot be negative" }
    }
}
