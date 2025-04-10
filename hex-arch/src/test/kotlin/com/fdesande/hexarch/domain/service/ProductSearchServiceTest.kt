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
        // Given
        whenever(repository.find(productQuery)).thenReturn(emptySet())

        // When
        val actual = service.search(productQuery)

        // Then
        assertThat(actual, emptyIterable())
    }

    @Test
    fun `search returns set obtained from repository`() {
        // Given
        val anotherProduct = createProduct(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        whenever(repository.find(productQuery)).thenReturn(setOf(sampleProduct, anotherProduct))

        // When
        val actual = service.search(productQuery)

        // Then
        assertThat(actual, contains(sampleProduct, anotherProduct))
    }

    @Test
    fun `searchById returns null set when repository returns null`() {
        // Given
        whenever(repository.findById(sampleId)).thenReturn(null)

        // When
        val actual = service.searchById(sampleId)

        // Then
        assertThat(actual, nullValue())
    }

    @Test
    fun `searchById returns set obtained from repository`() {
        // Given
        whenever(repository.findById(sampleId)).thenReturn(sampleProduct)

        // When
        val actual = service.searchById(sampleId)

        // Then
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
