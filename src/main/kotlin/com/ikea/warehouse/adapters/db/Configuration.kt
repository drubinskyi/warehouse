package com.ikea.warehouse.adapters.db

import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.mapping.event.BeforeConvertEvent
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
@EnableJdbcRepositories
class AggregateConfiguration : AbstractJdbcConfiguration() {
    @Bean
    fun idSetting(): ApplicationListener<*> {
        return ApplicationListener { event: BeforeConvertEvent<*> ->
            if (event.entity is ProductEntity) {
                val product = event.entity as ProductEntity
                product.id = product.idPlaceholder
            }
        }
    }

    @Bean
    fun namedParameterJdbcTemplate(operations: JdbcOperations): NamedParameterJdbcTemplate =
        NamedParameterJdbcTemplate(operations)
}
