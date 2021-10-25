package com.ikea.warehouse.adapters.db

import com.ikea.warehouse.core.model.Article
import com.ikea.warehouse.ports.ArticleService
import org.springframework.stereotype.Service

@Service
internal class ArticleServiceImpl(private val batchOperations: ArticleRepository): ArticleService {
    override fun upsert(articles: List<Article>) {
        batchOperations.upsert(articles.map { it.toEntity() })
    }
}
