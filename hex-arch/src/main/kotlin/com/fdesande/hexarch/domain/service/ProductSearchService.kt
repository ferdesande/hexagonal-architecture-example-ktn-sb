package com.fdesande.hexarch.domain.service

import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.domain.model.ProductQuery
import com.fdesande.hexarch.domain.port.input.ProductSearchUseCase
import com.fdesande.hexarch.domain.port.output.ProductRepository

class ProductSearchService(
    private val productRepository: ProductRepository
) : ProductSearchUseCase {
    override fun search(query: ProductQuery): Set<Product> {
        return productRepository.find(query)
    }

    override fun searchById(id: ProductId): Product? {
        return productRepository.findById(id)
    }
}
