package com.fdesande.hexarch.infrastructure.output.persistence.entitiy

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "products")
@Suppress("LongParameterList")
class ProductEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    val id: UUID? = null,

    @Column(nullable = false, length = 255)
    val name: String,

    @Column(columnDefinition = "TEXT")
    val description: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    val brand: BrandEntity,

    @OneToOne(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val price: ProductPriceEntity? = null,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val imageUrls: MutableList<ProductImageEntity> = mutableListOf(),

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: Instant? = null
)
