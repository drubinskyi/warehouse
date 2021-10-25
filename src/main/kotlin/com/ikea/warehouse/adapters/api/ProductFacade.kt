package com.ikea.warehouse.adapters.api

import com.google.gson.Gson
import com.ikea.warehouse.core.model.ArticleId
import com.ikea.warehouse.core.model.Product
import com.ikea.warehouse.core.model.ProductArticle
import com.ikea.warehouse.core.model.ProductId
import com.ikea.warehouse.ports.FileReader
import com.ikea.warehouse.ports.ProductService
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
internal class ProductFacade(
    private val productService: ProductService,
    private val fileReader: FileReader<MultipartFile>,
) {
    fun saveAll(file: MultipartFile): ProductUploadedResponse {
        val products = fileReader.read(file).toProductsRequest()
        val productsModel = products.toModel()
        productService.saveAll(productsModel)

        return ProductUploadedResponse(productsModel.map { it.id.id })
    }

    fun getAllWithQuantity(): List<ProductsQuantityResponse> =
        productService.getAllWithQuantity().map { ProductsQuantityResponse(id = it.key.id.id, amount = it.value) }


    fun sellProduct(id: String, quantity: Int) {
        productService.sellProduct(ProductId(id), quantity)
    }
}

private fun String.toProductsRequest(): ProductsRequest = Gson().fromJson(this, ProductsRequest::class.java)
private fun ProductsRequest.toModel(): List<Product> = this.products.map {
    Product(
        id = ProductId(UUID.randomUUID().toString()),
        name = it.name,
        price = it.price,
        articles = it.containArticles.map { article ->
            ProductArticle(articleId = ArticleId(article.articleId),
                amount = article.amount)
        }.toSet())
}
