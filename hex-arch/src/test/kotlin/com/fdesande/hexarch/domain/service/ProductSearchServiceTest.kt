package com.fdesande.hexarch.domain.service

import com.fdesande.hexarch.domain.model.Price
import com.fdesande.hexarch.domain.model.Product
import com.fdesande.hexarch.domain.model.ProductId
import com.fdesande.hexarch.domain.model.ProductQuery
import com.fdesande.hexarch.domain.port.output.ProductRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class ProductSearchServiceTest {

    private val sampleUUID = UUID.fromString("00000000-0000-0000-0000-000000000001")
    private val sampleId = ProductId(sampleUUID)
    private val productQuery = ProductQuery()
    private val sampleProduct = createProduct(sampleUUID)

    @Mock
    private lateinit var repository: ProductRepository

    private lateinit var service: ProductSearchService

    @BeforeEach
    fun setUp() {
        reset(repository)
        service = ProductSearchService(repository)
    }

    @Test
    fun `search returns empty set when repository returns an empty set`() {
        // Arrange
        whenever(repository.find(productQuery)).thenReturn(emptySet())

        // Act
        val actual = service.search(productQuery)

        // Assert
        assertThat(actual, emptyIterable())
    }

    @Test
    fun `search returns set obtained from repository`() {
        // Arrange
        val anotherProduct = createProduct(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        whenever(repository.find(productQuery)).thenReturn(setOf(sampleProduct, anotherProduct))

        // Act
        val actual = service.search(productQuery)

        // Assert
        assertThat(actual, contains(sampleProduct, anotherProduct))
    }

    @Test
    fun `searchById returns null set when repository returns null`() {
        // Arrange
        whenever(repository.findById(sampleId)).thenReturn(null)

        // Act
        val actual = service.searchById(sampleId)

        // Assert
        assertThat(actual, nullValue())
    }

    @Test
    fun `searchById returns set obtained from repository`() {
        // Arrange
        whenever(repository.findById(sampleId)).thenReturn(sampleProduct)

        // Act
        val actual = service.searchById(sampleId)

        // Assert
        assertThat(actual, equalTo(sampleProduct))
    }

    private fun createProduct(id: UUID) = Product(
        id = ProductId(id),
        name = "Product $id",
        description = "sample description",
        brand = "sample brand",
        price = Price(BigDecimal.valueOf(100.00)),
        imageUrls = emptyList(),
    )
}
