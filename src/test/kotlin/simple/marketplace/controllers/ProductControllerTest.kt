package simple.marketplace.controllers

import io.micronaut.core.type.Argument
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
    fun givenGetWithNonExistingId_thenReturnsNotFound() {
        val nonExistingId: Long = products[products.size - 1].id!! + 1
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange<Any>("/$nonExistingId")
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
    fun testGetAll() {
        val request = HttpRequest.GET<Product>("/")
        val response = client.toBlocking().exchange(request, Argument.listOf(Product::class.java))

        assertEquals(HttpStatus.OK, response.status)
        val items: List<Product> = response.body() ?: emptyList()
        assertTrue(items.size == 3)
        for (product in products) {
            assertTrue(items.contains(product))
        }
    }

    @Test
    fun testCreate() {
        val toCreate = generate()
        val request = HttpRequest.POST("/", toCreate)
        val response = client.toBlocking().exchange(request, Product::class.java)

        assertEquals(HttpStatus.OK, response.status)
        val created = response.body()
        assertTrue(created.id != null)
        toCreate.id = created.id
        assertEquals(toCreate, created)
    }

    @Test
    fun givenCreateWithId_thenReturnsBadRequest() {
        val toCreate = generate()
        toCreate.id = 123

        val request = HttpRequest.POST("/", toCreate)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Product::class.java)
        }
        assertNotNull(thrown.response)
        assertEquals(HttpStatus.BAD_REQUEST, thrown.status)
    }

    @Test
    fun testUpdate() {
        val toUpdate = products[1]
        toUpdate.name = "Updated name"
        toUpdate.description = "Updated description"
        toUpdate.price += 123.45f

        val id: Long = toUpdate.id!!
        val request = HttpRequest.PUT("/$id", toUpdate)
        val response = client.toBlocking().exchange(request, Product::class.java)

        assertEquals(HttpStatus.OK, response.status)
        val updated = response.body()
        assertEquals(toUpdate, updated)
    }

    @Test
    fun givenUpdateWithNoId_thenReturnsBadRequest() {
        val toUpdate = products[0]
        toUpdate.name = "Updated name"
        toUpdate.description = "Updated description"
        toUpdate.price += 43.43f

        val id: Long = toUpdate.id!!
        toUpdate.id = null
        val request = HttpRequest.PUT("/$id", toUpdate)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Product::class.java)
        }
        assertNotNull(thrown.response)
        assertEquals(HttpStatus.BAD_REQUEST, thrown.status)
    }

    @Test
    fun givenUpdateWithNonMatchingIds_thenReturnsBadRequest() {
        val toUpdate = products[2]
        toUpdate.name = "Updated name"
        toUpdate.description = "Updated description"
        toUpdate.price += 43.43f

        val id: Long = toUpdate.id!!
        toUpdate.id = toUpdate.id!! + 1
        val request = HttpRequest.PUT("/$id", toUpdate)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Product::class.java)
        }
        assertNotNull(thrown.response)
        assertEquals(HttpStatus.BAD_REQUEST, thrown.status)
    }

    @Test
    fun givenUpdateNonExistingProduct_thenReturnsNotFound() {
        val toUpdate = products[2]
        toUpdate.name = "Updated name"
        toUpdate.description = "Updated description"
        toUpdate.price += 43.43f

        val id: Long = toUpdate.id!! + 10
        toUpdate.id = toUpdate.id!! + 10
        val request = HttpRequest.PUT("/$id", toUpdate)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Product::class.java)
        }
        assertNotNull(thrown.response)
        assertEquals(HttpStatus.NOT_FOUND, thrown.status)
    }

    @Test
    fun testDelete() {
        val deleteId = products[0].id!!

        val request = HttpRequest.DELETE<Product>("/$deleteId")
        val response = client.toBlocking().exchange(request, Product::class.java)
        assertEquals(HttpStatus.NO_CONTENT, response.status)

        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange<Any>("/$deleteId")
        }
        assertNotNull(thrown.response)
        assertEquals(HttpStatus.NOT_FOUND, thrown.status)
    }

    @Test
    fun testDeleteNonExisting() {
        val deleteId = products[2].id!! + 1

        val request = HttpRequest.DELETE<Product>("/$deleteId")
        val response = client.toBlocking().exchange(request, Product::class.java)
        assertEquals(HttpStatus.NO_CONTENT, response.status)
    }
}