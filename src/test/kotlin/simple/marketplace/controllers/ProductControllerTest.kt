package simple.marketplace.controllers

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
        //TODO: implement
    }

    @Test
    fun testGet() {

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