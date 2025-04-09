package com.fdesande.hexarch.domain.port.output

import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.domain.model.ProductQuery

interface ProductRepository {
    fun find(query: ProductQuery): Set<Product>
    fun findById(id: ProductId): Product?
}
