package com.ikea.warehouse.adapters.api

import com.google.gson.annotations.SerializedName

internal data class ArticleRequest (val inventory: List<ArticleDTO>)

internal data class ArticleDTO(
    @SerializedName(value = "art_id")
    val id: String,
    val name: String,
    val stock: Int
)
