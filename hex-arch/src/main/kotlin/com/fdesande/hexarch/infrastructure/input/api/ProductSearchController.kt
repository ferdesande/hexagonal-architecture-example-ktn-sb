package com.fdesande.hexarch.infrastructure.input.api

import com.fdesande.hexarch.domain.model.ProductQuery
import com.fdesande.hexarch.domain.port.input.ProductSearchUseCase
import com.fdesande.hexarch.infrastructure.exception.EmptyResultException
import com.fdesande.hexarch.infrastructure.exception.ResourceNotFoundException
import com.fdesande.hexarch.infrastructure.input.api.dto.ProductDto
import com.fdesande.hexarch.infrastructure.input.api.mapper.ProductMapper
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/products")
class ProductSearchController(
    private val productSearchUseCase: ProductSearchUseCase,
    private val productMapper: ProductMapper
) {
    @GetMapping
    fun getProducts(
        @RequestParam("filter", required = false) filter: String? = null,
        @RequestParam("minPrice", required = false) minPrice: BigDecimal? = null,
        @RequestParam("maxPrice", required = false) maxPrice: BigDecimal? = null,
    ): List<ProductDto> {
        val query = ProductQuery(
            filter = filter,
            minPrice = minPrice,
            maxPrice = maxPrice,
        )
        val dtos = productSearchUseCase
            .search(query)
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
