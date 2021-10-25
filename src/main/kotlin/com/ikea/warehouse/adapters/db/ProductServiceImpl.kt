package com.ikea.warehouse.adapters.db

import com.ikea.warehouse.adapters.ArticlesNotLoadedException
import com.ikea.warehouse.adapters.ProductNotFoundException
import com.ikea.warehouse.adapters.ProductOutOfStockException
import com.ikea.warehouse.core.model.Product
import com.ikea.warehouse.core.model.ProductId
import com.ikea.warehouse.ports.ProductService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service

@Service
internal class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val articleRepository: ArticleRepository,
) : ProductService {
    override fun saveAll(products: List<Product>) {
        try {
            productRepository.saveAll(products.map { it.toEntity() })
        } catch (e: DbActionExecutionException) {
            if (e.cause is DataIntegrityViolationException) {
                if ((e.cause as DataIntegrityViolationException).localizedMessage.contains("is not present in table")) {
                    throw ArticlesNotLoadedException()
                }
            } else {
                throw e
            }
        }
    }

    override fun getAllWithQuantity(): Map<Product, Int> {
        val products = productRepository.findAll()
        val articles: List<ArticleEntity> = articleRepository.findAll().toList()

        return products.map {
            it.toModel() to calculateQuantity(it.articles, articles)
        }.toMap()
    }

    override fun sellProduct(id: ProductId, quantity: Int) {
        val product = productRepository.findById(id.id).orElseThrow { ProductNotFoundException(id) }
        val updateStockCommands = product.articles.map {
            UpdateStockParameters(id = it.articleId,
                stock = it.amount * quantity)
        }
        try {
            articleRepository.updateStock(updateStockCommands)
        } catch (e: DataIntegrityViolationException) {
            if (e.localizedMessage.contains("stock_non_negative")) {
                throw ProductOutOfStockException(id, quantity)
            } else {
              throw e
            }
        }
    }

    private fun calculateQuantity(productArticles: Set<ProductArticleEntity>, articles: List<ArticleEntity>): Int =
        productArticles.map { productArticle ->
            articles.firstOrNull { article -> productArticle.articleId == article.id }
                ?.stock?.div(productArticle.amount) ?: 0
        }.minOrNull() ?: 0
}
