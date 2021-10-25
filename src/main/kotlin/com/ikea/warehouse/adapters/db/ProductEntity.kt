package com.ikea.warehouse.adapters.db

import com.ikea.warehouse.adapters.ProductNotPersistedException
import com.ikea.warehouse.core.model.ArticleId
import com.ikea.warehouse.core.model.Product
import com.ikea.warehouse.core.model.ProductArticle
import com.ikea.warehouse.core.model.ProductId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("products")
internal data class ProductEntity(
    /*
    * Making id field nullable and assigning null as default value is a workaround
    * Implemented in that way because Spring Data JDBC treats project with non-null Id as existing and tries to apply update
    */
    @Id
    var id: String? = null,
    val name: String,
    val price: BigDecimal,
    @MappedCollection(idColumn = "product_id")
    val articles: Set<ProductArticleEntity>,
    @Transient
    var idPlaceholder: String? = null
) {
    @PersistenceConstructor
    constructor(
        id: String,
        name: String,
        price: BigDecimal,
        articles: Set<ProductArticleEntity>
    ) : this(id, name, price, articles, null)

    fun toModel() = Product(
        id = ProductId(this.id ?: throw ProductNotPersistedException()),
        name = this.name,
        price = this.price,
        articles = this.articles.map { ProductArticle(articleId = ArticleId(it.articleId), amount = it.amount) }.toSet()
    )
}

internal fun Product.toEntity() = ProductEntity(
    name = this.name,
    price = this.price,
    articles = this.articles.map { ProductArticleEntity(articleId = it.articleId.id, amount = it.amount) }.toSet(),
    idPlaceholder = this.id.id
)
