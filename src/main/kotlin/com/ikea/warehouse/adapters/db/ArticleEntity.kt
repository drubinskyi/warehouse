package com.ikea.warehouse.adapters.db

import com.ikea.warehouse.core.model.Article
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("articles")
internal data class ArticleEntity (
    @Id
    val id: String,
    val name: String,
    val stock: Int
)

internal fun Article.toEntity() = ArticleEntity(id = this.id.id, name = this.name, stock = this.stock)
