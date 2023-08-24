package simple.marketplace.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import simple.marketplace.entities.Product
import simple.marketplace.repositories.ProductRepository
import kotlin.random.Random

@MicronautTest
class ProductControllerTest(
    @Client("/products") val client: HttpClient,
    private val repository: ProductRepository
) {

    private val products: MutableList<Product> = ArrayList()

    //TODO: refactor helper functions
    private var id: Int = 0
    private fun generate(): Product {
        val product = Product(null, "test product - $id", "test description - $id", Random.nextFloat() * 10)
        id++
        return product
    }

    private fun generateAndSave(): Product {
        val product = generate()
        return repository.save(product)
    }

    @BeforeEach
    fun prepareData() {
        products.add(generateAndSave())
        products.add(generateAndSave())
        products.add(generateAndSave())
    }

    @AfterEach
    fun teardown() {
        repository.deleteAll()
        products.clear()
    }

    @Test
    fun testGetNotFound() {
        val lastId: Long = products[products.size - 1].id!! + 1
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange<Any>("/$lastId")
        }
        assertNotNull(thrown.response)
        assertEquals(HttpStatus.NOT_FOUND, thrown.status)
    }

    @Test
    fun testGet() {
        val toGet: Product = products[0]
        val id: Long = toGet.id!!

        val request = HttpRequest.GET<Product>("/$id")
        val response = client.toBlocking().exchange(request, Product::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(toGet, response.body())
    }

    @Test
    fun testCreate() {
    }

    @Test
    fun testUpdate() {

    }

    @Test
    fun testDelete() {

    }
}