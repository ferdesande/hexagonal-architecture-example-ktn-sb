package com.fdesande.hexarch.domain.model

import java.util.*

data class Image(
    val id: UUID,
    val url: String,
    val name: String
) {
    init {
        require(url.isNotBlank()) { "Image URL cannot be blank" }
    }
}
