package com.ikea.warehouse.core.model

data class Article (
    val id: ArticleId,
    val name: String,
    val stock: Int
)

inline class ArticleId(val id: String)
