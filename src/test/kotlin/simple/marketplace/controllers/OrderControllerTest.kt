package simple.marketplace.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import simple.marketplace.entities.Order
import simple.marketplace.entities.Product
import simple.marketplace.repositories.OrderRepository
import simple.marketplace.repositories.ProductRepository

@MicronautTest
class OrderControllerTest(
    @Client("/orders") val client: HttpClient
) {

    @Inject
    private lateinit var productRepository: ProductRepository

    @Inject
    private lateinit var orderRepository: OrderRepository

    private var id: Int = 0
    private fun generate(): Order {
        val createdProduct =
            productRepository.save(Product(null, "Test Product $id", "Test Product Desc $id", 12.53f))
        if (createdProduct.id != null) {
            return Order(null, createdProduct.id!!, 10)
        }
        throw RuntimeException("Failed to generate valid product")
    }

    @AfterEach
    fun teardown() {
        productRepository.deleteAll()
        orderRepository.deleteAll()
    }

    @Test
    fun testCreate() {
        val toCreate = generate()
        toCreate.id = null

        val request = HttpRequest.POST("/", toCreate)
        val response = client.toBlocking().exchange(request, Order::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        val created = response.body()
        Assertions.assertTrue(created.id != null)
        toCreate.id = created.id
        Assertions.assertEquals(toCreate, created)
    }

    @Test
    fun givenCreateWithId_thenReturnsBadRequest() {
        val toCreate = generate()
        toCreate.id = 123

        val request = HttpRequest.POST("/", toCreate)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Order::class.java)
        }
        Assertions.assertNotNull(thrown.response)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.status)
    }

    @Test
    fun givenCreateOrderWithInvalidProductId_ThenThrowsException() {
        val order = generate()
        order.product_id = order.product_id + 1000

        val request = HttpRequest.POST("/", order)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Order::class.java)
        }
        Assertions.assertNotNull(thrown.response)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.status)
    }

    @Test
    fun givenCreateOrderWithNegativeQuantity_ThenThrowsException() {
        val order = generate()
        order.quantity = -100

        val request = HttpRequest.POST("/", order)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Order::class.java)
        }
        Assertions.assertNotNull(thrown.response)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.status)
    }
}