package com.fdesande.hexarch.infrastructure.output.persistence.repository

import com.fdesande.hexarch.infrastructure.output.persistence.entitiy.ProductEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductEntityRepository : CrudRepository<ProductEntity, UUID>
