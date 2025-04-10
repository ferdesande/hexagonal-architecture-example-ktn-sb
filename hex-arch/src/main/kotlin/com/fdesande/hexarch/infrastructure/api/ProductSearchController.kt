package com.fdesande.hexarch.infrastructure.api

import com.fdesande.hexarch.domain.model.ProductQuery
import com.fdesande.hexarch.domain.port.input.ProductSearchUseCase
import com.fdesande.hexarch.infrastructure.api.dto.ProductDto
import com.fdesande.hexarch.infrastructure.api.mapper.ProductMapper
import com.fdesande.hexarch.infrastructure.exception.EmptyResultException
import com.fdesande.hexarch.infrastructure.exception.ResourceNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/products")
class ProductSearchController(
    private val productSearchUseCase: ProductSearchUseCase,
    private val productMapper: ProductMapper
) {
    @GetMapping
    fun getProducts(): List<ProductDto> {
        val dtos = productSearchUseCase
            .search(ProductQuery())
            .mapNotNull { productMapper.toDto(it) }

        if (dtos.isNotEmpty()) {
            return dtos
        } else {
            throw EmptyResultException("No products where found for the current query")
        }
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: UUID): ProductDto {
        return productSearchUseCase
            .searchById(productMapper.uuidToProductId(id))
            ?.let { productMapper.toDto(it) }
            ?: throw ResourceNotFoundException("Product with id: $id not found")
    }
}
