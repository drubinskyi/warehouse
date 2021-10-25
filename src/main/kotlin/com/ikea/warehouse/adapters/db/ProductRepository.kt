package com.ikea.warehouse.adapters.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
internal interface ProductRepository : CrudRepository<ProductEntity, String>
