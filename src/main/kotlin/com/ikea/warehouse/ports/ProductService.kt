package com.ikea.warehouse.ports

import com.ikea.warehouse.core.model.Product
import com.ikea.warehouse.core.model.ProductId

interface ProductService {
    fun saveAll(products: List<Product>)
    fun getAllWithQuantity(): Map<Product, Int>
    fun sellProduct(id: ProductId, quantity: Int)
}
