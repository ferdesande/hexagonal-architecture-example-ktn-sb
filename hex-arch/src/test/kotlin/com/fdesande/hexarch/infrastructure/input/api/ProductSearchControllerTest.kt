package com.fdesande.hexarch.infrastructure.input.api

import com.fdesande.hexarch.domain.model.Image
import com.fdesande.hexarch.domain.model.Price
import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.infrastructure.config.TestcontainersConfiguration
import com.fdesande.hexarch.infrastructure.input.api.dto.ImageDto
import com.fdesande.hexarch.infrastructure.input.api.dto.PriceDto
import com.fdesande.hexarch.infrastructure.input.api.dto.ProductDto
import com.fdesande.hexarch.infrastructure.input.api.exception.ErrorResponse
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [TestcontainersConfiguration.Initializer::class])
@DBRider
@DataSet("query-products/fixtures.json")
class ProductSearchControllerTest {

    companion object {
        private const val FILTER_PARAM = "filter"
        private const val MIN_PRICE_PARAM = "minPrice"
        private const val MAX_PRICE_PARAM = "maxPrice"
    }

    private val product1 = Product(
        id = ProductId(UUID.fromString("00000000-0000-0000-0000-000000000001")),
        description = "A fancy hoodie with a blue camouflage",
        name = "Blue camouflage hoodie",
        brand = "Nike",
        price = Price(BigDecimal.valueOf(37.21)),
        imageUrls = listOf(
            Image(
                id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
                url = "https://img/nike/hoodie-front.png",
                name = "Front"
            ),
            Image(
                id = UUID.fromString("00000000-0000-0001-0001-000000000002"),
                url = "https://img/nike/hoodie-front-with-model.png",
                name = "Front with model"
            )
        )
    )
    private val product2 = Product(
        id = ProductId(UUID.fromString("00000000-0000-0000-0000-000000000002")),
        description = "Top performance football shoes",
        name = "Adidas predator 2025",
        brand = "Adidas",
        price = Price(BigDecimal.valueOf(125.99)),
        imageUrls = listOf(
            Image(
                id = UUID.fromString("00000000-0000-0001-0002-000000000001"),
                name = "Red Predator",
                url = "https://img/adidas/predator-25-red.png",
            ),
            Image(
                id = UUID.fromString("00000000-0000-0001-0002-000000000002"),
                name = "Blue Predator",
                url = "https://img/adidas/predator-25-blue.png",
            ),
            Image(
                id = UUID.fromString("00000000-0000-0001-0002-000000000003"),
                name = "Pink Predator",
                url = "https://img/adidas/predator-25-pink.png",
            )
        )
    )
    private val product3 = Product(
        id = ProductId(UUID.fromString("00000000-0000-0000-0000-000000000003")),
        description = "The Argentinian official football T-Shirt 2025",
        name = "Argentina football T-Shirt 2025",
        brand = "Adidas",
        price = Price(BigDecimal.valueOf(90.00)),
        imageUrls = emptyList()
    )

    @LocalServerPort
    private val localPort = 0

    @BeforeEach
    fun setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.baseURI = "http://localhost:$localPort/products"
    }

    @Test
    fun `get products return dtos when data is found`() {
        val expectedProducts = listOf(product1, product2, product3).map(::createProductDto)
        val actualProducts = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getList("", ProductDto::class.java)
        }

        // Then
        assertThat(actualProducts, equalTo(expectedProducts))
    }

    @Test
    fun `get products return filtered result by name`() {
        val expectedProducts = listOf(product1).map(::createProductDto)
        val actualProducts = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            queryParams(FILTER_PARAM, "CAMOUFLAGE")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getList("", ProductDto::class.java)
        }

        // Then
        assertThat(actualProducts, equalTo(expectedProducts))
    }

    @Test
    fun `get products return filtered result by description`() {
        val expectedProducts = listOf(product2, product3).map(::createProductDto)
        val actualProducts = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            queryParams(FILTER_PARAM, "foot")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getList("", ProductDto::class.java)
        }

        // Then
        assertThat(actualProducts, equalTo(expectedProducts))
    }

    @Test
    fun `get products return filtered result by brand`() {
        val expectedProducts = listOf(product2, product3).map(::createProductDto)
        val actualProducts = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            queryParams(FILTER_PARAM, "adidas")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getList("", ProductDto::class.java)
        }

        // Then
        assertThat(actualProducts, equalTo(expectedProducts))
    }

    @Test
    fun `get products return filtered result by min price`() {
        val expectedProducts = listOf(product2, product3).map(::createProductDto)
        val actualProducts = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            queryParams(MIN_PRICE_PARAM, "45.00")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getList("", ProductDto::class.java)
        }

        // Then
        assertThat(actualProducts, equalTo(expectedProducts))
    }

    @Test
    fun `get products return filtered result by max price`() {
        val expectedProducts = listOf(product1, product3).map(::createProductDto)
        val actualProducts = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            queryParams(MAX_PRICE_PARAM, "100.00")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getList("", ProductDto::class.java)
        }

        // Then
        assertThat(actualProducts, equalTo(expectedProducts))
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "$FILTER_PARAM, Brooks",
            "$MIN_PRICE_PARAM, 1342.12",
            "$MAX_PRICE_PARAM, 1.99",
        ]
    )
    fun `get products return an empty list when no product is found`(parameter: String, value: String) {
        Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            queryParams(parameter, value)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.SC_OK)
            body("", emptyIterable<ProductDto>())
        }
    }

    @Test
    fun `get product by id return dto when data is found`() {
        val productUUID = product1.id.id
        val dto = Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            pathParam("id", productUUID)
        } When {
            get("/{id}")
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            body().jsonPath().getObject("", ProductDto::class.java)
        }

        // Then
        assertThat(dto, notNullValue())
        assertThat(dto, equalTo(createProductDto(product1)))
    }

    @Test
    fun `get product by id return not found when product does not exist`() {
        // Given
        val productUUID = "99999999-9999-9999-9999-999999999999"

        Given {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            pathParam("id", productUUID)
        } When {
            get("/{id}")
        } Then {
            statusCode(HttpStatus.SC_NOT_FOUND)
            body("timestamp", notNullValue())
            body("message", equalTo("Product with id: $productUUID not found"))
            body("status", equalTo(HttpStatus.SC_NOT_FOUND))
            body("error", equalTo("NOT_FOUND"))
        } Extract {
            body().jsonPath().getObject("", ErrorResponse::class.java)
        }
    }

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
