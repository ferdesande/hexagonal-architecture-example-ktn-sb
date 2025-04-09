package com.fdesande.hexarch.infrastructure.config

import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.domain.model.ProductQuery
import com.fdesande.hexarch.domain.port.input.ProductSearchUseCase
import com.fdesande.hexarch.domain.port.output.ProductRepository
import com.fdesande.hexarch.domain.service.ProductSearchService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductConfiguration {
    @Bean
    fun productRepository(): ProductRepository = object : ProductRepository {
        override fun find(query: ProductQuery): Set<Product> = emptySet()
        override fun findById(id: ProductId): Product? = null
    }

    @Bean
    fun productSearchUseCase(
        productRepository: ProductRepository
    ): ProductSearchUseCase = ProductSearchService(productRepository)
}
