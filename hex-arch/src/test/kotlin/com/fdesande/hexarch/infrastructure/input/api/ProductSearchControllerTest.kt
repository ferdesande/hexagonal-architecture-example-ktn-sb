package com.fdesande.hexarch.infrastructure.input.api

import com.fdesande.hexarch.domain.model.*
import com.fdesande.hexarch.domain.port.input.ProductSearchUseCase
import com.fdesande.hexarch.infrastructure.input.api.dto.ImageDto
import com.fdesande.hexarch.infrastructure.input.api.dto.PriceDto
import com.fdesande.hexarch.infrastructure.input.api.dto.ProductDto
import com.fdesande.hexarch.infrastructure.input.api.exception.ErrorResponse
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductSearchControllerTest {

    private val image1 = Image(UUID.randomUUID(), "https://some.url", "first image")
    private val image2 = Image(UUID.randomUUID(), "https://another.url", "first image")

    @MockBean
    private lateinit var productSearchUseCase: ProductSearchUseCase

    @LocalServerPort
    private val localPort = 0

    @BeforeEach
    fun setUp() {
        reset(productSearchUseCase)
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.baseURI = "http://localhost:$localPort/products"
    }

    @Test
    fun `get products return dtos when data is found`() {
        // Given
        val query = ProductQuery()
        val products = (0..4).map { createProduct(it) }.toSet()
        whenever(productSearchUseCase.search(query)).thenReturn(products)

        val dtos = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getList("", ProductDto::class.java)
        }

        // Then
        assertThat(dtos, hasSize(products.size))
        assertThat(dtos, equalTo(products.map(::createProductDto)))
    }

    @Test
    fun `get products return an empty list when no product is found`() {
        // Given
        val query = ProductQuery()
        whenever(productSearchUseCase.search(query)).thenReturn(emptySet())

        Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
            body("", emptyIterable<ProductDto>())
        }
    }

    @Test
    fun `get product by id return dto when data is found`() {
        // Given
        val product = createProduct()
        whenever(productSearchUseCase.searchById(product.id)).thenReturn(product)

        val dto = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            pathParam("id", product.id.id)
        } When {
            get("/{id}")
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getObject("", ProductDto::class.java)
        }

        // Then
        assertThat(dto, notNullValue())
        assertThat(dto, equalTo(createProductDto(product)))
    }

    @Test
    fun `get product by id return not found when product does not exist`() {
        // Given
        val product = createProduct()
        whenever(productSearchUseCase.searchById(product.id)).thenReturn(null)

        Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            pathParam("id", product.id.id)
        } When {
            get("/{id}")
        } Then {
            statusCode(HttpStatus.SC_NOT_FOUND)
            body("timestamp", notNullValue())
            body("message", equalTo("Product with id: ${product.id.id} not found"))
            body("status", equalTo(HttpStatus.SC_NOT_FOUND))
            body("error", equalTo("NOT_FOUND"))
        } Extract {
            body().jsonPath().getObject("", ErrorResponse::class.java)
        }
    }

    private fun createProduct(
        productNumber: Int = 1
    ) = Product(
        id = ProductId(UUID.randomUUID()),
        name = "product $productNumber",
        description = "sample description",
        brand = "some brand",
        price = Price(BigDecimal.valueOf(productNumber * 10.00)),
        imageUrls = listOf(image1, image2),
    )

    private fun createProductDto(
        product: Product
    ) = ProductDto(
        id = product.id.id,
        name = product.name,
        description = product.description,
        brand = product.brand,
        price = PriceDto(product.price.amount),
        imageUrls = product.imageUrls.map(::toDto),
    )

    private fun toDto(image: Image) = ImageDto(url = image.url, name = image.name)
}
