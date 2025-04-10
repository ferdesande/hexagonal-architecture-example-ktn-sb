package com.fdesande.hexarch.infrastructure.output.persistence.entitiy

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product_images")
class ProductImageEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    val id: UUID? = null,

    @Column(nullable = false, length = 255)
    val name: String,

    @Column(nullable = false, length = 255)
    val url: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity
)
