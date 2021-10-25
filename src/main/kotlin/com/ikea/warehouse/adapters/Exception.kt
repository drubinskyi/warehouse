package com.ikea.warehouse.adapters

import com.ikea.warehouse.core.model.ProductId

abstract class ResourceNotFoundException(message: String) : RuntimeException(message)
abstract class BadRequestException(message: String) : RuntimeException(message)

class ProductNotFoundException(id: ProductId) : ResourceNotFoundException("Product with ${id.id} not found")
class ProductNotPersistedException : RuntimeException("Product with is not persisted yet")
class ArticlesNotLoadedException : BadRequestException("Can't load product since articles don't exist")
class ProductOutOfStockException(id: ProductId, quantity: Int) :
    BadRequestException("Product with ${id.id} is not available in quantity=$quantity")
