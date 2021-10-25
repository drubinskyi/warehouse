package com.ikea.warehouse.adapters.api

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal data class ProductsRequest(val products: List<ProductDTO>)

internal data class ProductDTO(
    val name: String,
    val price: BigDecimal,
    @SerializedName(value = "contain_articles")
    val containArticles: Set<ProductArticlesDTO>,
)

internal data class ProductArticlesDTO(
    @SerializedName(value = "art_id")
    val articleId: String,
    @SerializedName(value = "amount_of")
    val amount: Int
)
