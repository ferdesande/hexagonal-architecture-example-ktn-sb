package com.fdesande.hexarch.infrastructure.output.persistence.entitiy

import javax.persistence.*

@Entity
@Table(name = "brands")
class BrandEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, length = 255, unique = true)
    val name: String,

    @Column(columnDefinition = "TEXT")
    val description: String = ""
)
