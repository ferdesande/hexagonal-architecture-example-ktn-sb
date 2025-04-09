package com.fdesande.hexarch.domain.port.input

import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductQuery

interface ProductSearchUseCase {
    fun search(query: ProductQuery): Set<Product>
    fun searchById(id: ProductId): Product?
}
