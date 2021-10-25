package com.ikea.warehouse.adapters.db

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
internal class ArticleRepository(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun upsert(articles: List<ArticleEntity>) {
        namedParameterJdbcTemplate.batchUpdate(
            """insert into articles (
                                                 id,
                                                 name,
                                                 stock
                                              )
                                               values (
                                                 :id,
                                                 :name,
                                                 :stock
                                               ) on conflict (id) do update set
                                                  name = :name,
                                                  stock = :stock""",
            articles.sortedBy { it.id }
                .map {
                    mapOf(
                        "id" to it.id,
                        "name" to it.name,
                        "stock" to it.stock,
                    )
                }.toTypedArray()
        )
    }

    fun updateStock(updates: List<UpdateStockParameters>) {
        namedParameterJdbcTemplate.batchUpdate("update articles set stock = stock - :stock where id = :id",
            updates.sortedBy { it.id }
                .map {
                    mapOf(
                        "id" to it.id,
                        "stock" to it.stock,
                    )
                }.toTypedArray()
        )
    }

    fun findAll(): List<ArticleEntity> = namedParameterJdbcTemplate.query("""select * from articles""")
    { resultSet, _ -> createArticleEntity(resultSet) }

    private fun createArticleEntity(resultSet: ResultSet) = ArticleEntity(
        resultSet.getString("id"),
        resultSet.getString("name"),
        resultSet.getInt("stock")
    )
}
