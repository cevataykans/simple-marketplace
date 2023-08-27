package simple.marketplace.services

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import simple.marketplace.entities.Order
import simple.marketplace.entities.Product
import simple.marketplace.repositories.OrderRepository
import simple.marketplace.repositories.ProductRepository

@MicronautTest
class OrderServiceTest {

    @Inject
    private lateinit var orderService: OrderService

    @Inject
    private lateinit var productService: ProductService

    @Inject
    private lateinit var productRepository: ProductRepository

    @Inject
    private lateinit var orderRepository: OrderRepository

    private var id: Int = 0
    private fun generate(): Order {
        val createdProduct =
            productService.create(Product(null, "Order Test Product $id", "Order Test Product Description $id", 10.54f))
        id++
        return Order(null, createdProduct.id!!, 10)
    }

    @AfterEach
    fun teardown() {
        productRepository.deleteAll()
        orderRepository.deleteAll()
    }

    @Test
    fun testCreate() {
        val order = generate()
        val created = orderService.create(order)
        Assertions.assertTrue(created.id != null)
        order.id = created.id
        Assertions.assertEquals(order, created)
    }

    @Test
    fun givenCreateOrderWithId_ThenThrowsException() {
        val order = generate()
        order.id = 1
        Assertions.assertThrows(Exception::class.java) {
            orderService.create(order)
        }
    }

    @Test
    fun givenCreateOrderWithInvalidProductId_ThenThrowsException() {
        val order = generate()
        order.product_id = order.product_id + 1000
        Assertions.assertThrows(Exception::class.java) {
            orderService.create(order)
        }
    }

    @Test
    fun givenCreateOrderWithNegativeQuantity_ThenThrowsException() {
        val order = generate()
        order.quantity = -100
        Assertions.assertThrows(Exception::class.java) {
            orderService.create(order)
        }
    }
}