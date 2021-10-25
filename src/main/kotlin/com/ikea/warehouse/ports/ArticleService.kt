package com.ikea.warehouse.ports

import com.ikea.warehouse.core.model.Article

interface ArticleService {
    fun upsert(articles: List<Article>)
}
