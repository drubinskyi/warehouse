package com.ikea.warehouse.adapters.api

import com.google.gson.Gson
import com.ikea.warehouse.core.model.Article
import com.ikea.warehouse.core.model.ArticleId
import com.ikea.warehouse.ports.ArticleService
import com.ikea.warehouse.ports.FileReader
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
internal class ArticleFacade(
    private val articleService: ArticleService,
    private val fileReader: FileReader<MultipartFile>,
) {
    fun upsert(file: MultipartFile): ArticleUploadedResponse {
        val inventory = fileReader.read(file).toArticleRequest()
        articleService.upsert(inventory.toModel())

        return ArticleUploadedResponse(inventory.inventory.map { it.id })
    }
}

private fun String.toArticleRequest(): ArticleRequest = Gson().fromJson(this, ArticleRequest::class.java)
private fun ArticleRequest.toModel(): List<Article> = this.inventory.map {
    Article(id = ArticleId(it.id), name = it.name, stock = it.stock)
}
