package simple.marketplace.repositories

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import jakarta.inject.Singleton
import simple.marketplace.entities.Product

@Singleton
@JdbcRepository(dialect = Dialect.POSTGRES)
interface ProductRepository : CrudRepository<Product, Long> {
}