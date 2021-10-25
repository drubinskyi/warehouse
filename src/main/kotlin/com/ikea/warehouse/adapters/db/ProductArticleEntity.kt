package com.ikea.warehouse.adapters.db

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("product_article")
internal data class ProductArticleEntity (
    @Column("article_id")
    val articleId: String,
    val amount: Int
)
