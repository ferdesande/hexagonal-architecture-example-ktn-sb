package com.fdesande.hexarch.infrastructure.output.persistence.adapter

import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.domain.model.ProductQuery
import com.fdesande.hexarch.domain.port.output.ProductRepository
import com.fdesande.hexarch.infrastructure.output.persistence.mapper.ProductEntityMapper
import com.fdesande.hexarch.infrastructure.output.persistence.repository.ProductEntityRepository
import org.springframework.stereotype.Component

@Component
class ProductRepositoryAdapter(
    private val productRepository: ProductEntityRepository,
    private val productEntityMapper: ProductEntityMapper
) : ProductRepository {
    override fun find(query: ProductQuery): Set<Product> {
        // Hint: In a further step the filter will be done against the database
        val lowercaseFilter = query.filter?.lowercase()
        return productRepository.findAll()
            .asSequence()
            .filter {
                lowercaseFilter == null ||
                    it.name.lowercase().contains(lowercaseFilter) ||
                    it.description.lowercase().contains(lowercaseFilter) ||
                    it.brand.name.lowercase().contains(lowercaseFilter)
            }
            .filter { query.minPrice == null || it.price!!.amount >= query.minPrice }
            .filter { query.maxPrice == null || it.price!!.amount <= query.maxPrice }
            .map(productEntityMapper::toProduct)
            .toSet()
    }

    override fun findById(productId: ProductId): Product? {
        return productRepository.findById(productId.id)
            .map(productEntityMapper::toProduct)
            .orElse(null)
    }
}
