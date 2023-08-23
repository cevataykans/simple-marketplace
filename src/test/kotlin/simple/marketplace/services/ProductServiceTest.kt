package simple.marketplace.services

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import simple.marketplace.entities.Product

@MicronautTest
class ProductServiceTest {

    @Inject
    lateinit var service: ProductService

    @Test
    fun testCreation() {
        val product = Product(id = null, name = "test", description = "test desctiption", price = 1.0f)
        val created = service.create(product)
        Assertions.assertTrue(product == created)
        Assertions.assertTrue(created.id != null)
    }
}