package com.fdesande.hexarch.infrastructure.input.api

import com.fdesande.hexarch.domain.model.ProductQuery
import com.fdesande.hexarch.domain.port.input.ProductSearchUseCase
import com.fdesande.hexarch.infrastructure.exception.EmptyResultException
import com.fdesande.hexarch.infrastructure.exception.ResourceNotFoundException
import com.fdesande.hexarch.infrastructure.input.api.dto.ProductDto
import com.fdesande.hexarch.infrastructure.input.api.exception.ErrorResponse
import com.fdesande.hexarch.infrastructure.input.api.mapper.ProductMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/products")
@Tag(name = "Product Search", description = "Endpoints to fetch products")
class ProductSearchController(
    private val productSearchUseCase: ProductSearchUseCase,
    private val productMapper: ProductMapper
) {
    @Operation(
        summary = "Find Products",
        description = "Get products from database filtered by name, brand, description or price.",
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
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

    @Operation(
        summary = "Find Product by Product ID",
        description = "Get a product from database filtered by ID.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = [Content(schema = Schema(implementation = ProductDto::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: UUID): ProductDto {
        return productSearchUseCase
            .searchById(productMapper.uuidToProductId(id))
            ?.let { productMapper.toDto(it) }
            ?: throw ResourceNotFoundException("Product with id: $id not found")
    }
}
