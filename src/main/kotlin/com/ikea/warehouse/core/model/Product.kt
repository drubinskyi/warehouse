package com.ikea.warehouse.core.model

import java.math.BigDecimal

data class Product(
    val id: ProductId,
    val name: String,
    val price: BigDecimal,
    val articles: Set<ProductArticle>,
)

inline class ProductId(val id: String)
