package com.fdesande.hexarch.infrastructure.persistence.entitiy

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product_prices")
class ProductPriceEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    val id: UUID? = null,

    @Column(nullable = false, precision = 15, scale = 4)
    val amount: BigDecimal,

    @Column(length = 3, nullable = false)
    val currencyCode: String = "EUR",

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity
)
