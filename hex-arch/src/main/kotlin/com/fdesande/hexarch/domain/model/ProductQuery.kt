package com.fdesande.hexarch.domain.model

import java.math.BigDecimal

data class ProductQuery(
    val filter: String? = null,
    val minPrice: BigDecimal? = null,
    val maxPrice: BigDecimal? = null,
)
