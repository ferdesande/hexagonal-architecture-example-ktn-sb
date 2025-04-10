package com.fdesande.hexarch.infrastructure.config

import com.fdesande.hexarch.domain.port.input.ProductSearchUseCase
import com.fdesande.hexarch.domain.port.output.ProductRepository
import com.fdesande.hexarch.domain.service.ProductSearchService
import com.fdesande.hexarch.infrastructure.output.persistence.adapter.ProductRepositoryAdapter
import com.fdesande.hexarch.infrastructure.output.persistence.mapper.ProductEntityMapper
import com.fdesande.hexarch.infrastructure.output.persistence.repository.ProductEntityRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductConfiguration {
    @Bean
    fun productRepository(
        productRepository: ProductEntityRepository,
        productEntityMapper: ProductEntityMapper
    ): ProductRepository = ProductRepositoryAdapter(productRepository, productEntityMapper)

    @Bean
    fun productSearchUseCase(
        productRepository: ProductRepository
    ): ProductSearchUseCase = ProductSearchService(productRepository)
}
