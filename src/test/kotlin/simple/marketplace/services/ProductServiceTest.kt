package simple.marketplace.services

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import simple.marketplace.entities.Product
import simple.marketplace.repositories.ProductRepository
import kotlin.random.Random

@MicronautTest
class ProductServiceTest {

    @Inject
    private lateinit var service: ProductService

    @Inject
    private lateinit var repository: ProductRepository

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

    @AfterEach
    fun resetDB() {
        repository.deleteAll()
    }

    @Test
    fun testCreate() {
        val product = generate()
        val created = service.create(product)
        Assertions.assertTrue(created.id != null)
        Assertions.assertEquals(product, created)
    }

    @Test
    fun givenCreateProductWithId_ThenThrowsException() {
        val product = generate()
        product.id = 10
        Assertions.assertThrows(Exception::class.java) {
            service.create(product)
        }
    }

    @Test
    fun testUpdate() {
        val original = generateAndSave()
        var toUpdate = Product(original.id, original.name, original.description, original.price)

        // name can be changed
        toUpdate.name = "Cevat"
        var updated = service.update(toUpdate)
        Assertions.assertEquals(toUpdate, updated)
        Assertions.assertNotEquals(original.name, updated.name)
        Assertions.assertEquals(original.id, updated.id)

        // description can be changed
        toUpdate.description = "My Description"
        updated = service.update(toUpdate)
        Assertions.assertEquals(toUpdate, updated)
        Assertions.assertNotEquals(original.description, updated.description)
        Assertions.assertEquals(original.id, updated.id)

        // price can be changed
        toUpdate.price = updated.price + 1.0f
        updated = service.update(toUpdate)
        Assertions.assertEquals(toUpdate, updated)
        Assertions.assertNotEquals(original.price, updated.price)
        Assertions.assertEquals(original.id, updated.id)

        // all properties can be changed back to original
        toUpdate = original.copy()
        updated = service.update(toUpdate)
        Assertions.assertEquals(toUpdate, updated)
        Assertions.assertEquals(original, updated)
    }

    @Test
    fun givenUpdateProductWithNoId_ThenThrowsException() {
        val toUpdate = generate()
        toUpdate.id = null
        Assertions.assertThrows(Exception::class.java) {
            service.update(toUpdate)
        }
    }

    @Test
    fun givenUpdateNotExistingProduct_ThenThrowsException() {
        val toUpdate = generateAndSave()
        toUpdate.id = toUpdate.id!! + 1
        Assertions.assertThrows(Exception::class.java) {
            service.update(toUpdate)
        }
    }

    @Test
    fun testGet() {
        val created = generateAndSave()
        val found = service.getById(created.id!!)
        Assertions.assertEquals(created, found)
    }

    @Test
    fun givenGetNotExistingProduct_ThenThrowsException() {
        val nonExistingId: Long = 12_000
        Assertions.assertThrows(Exception::class.java) {
            service.getById(nonExistingId)
        }
    }

    @Test
    fun testGetAll() {

        // retrieves no object
        var products = service.getAll()
        Assertions.assertTrue(products.isEmpty())

        val product1 = generateAndSave()
        val product2 = generateAndSave()

        // retrieves all saved 2 products
        products = service.getAll()
        Assertions.assertTrue(products.contains(product1))
        Assertions.assertTrue(products.contains(product2))
    }

    @Test
    fun testDelete() {
        val product = generateAndSave()
        service.delete(product.id!!)

        Assertions.assertThrows(Exception::class.java) {
            service.getById(product.id!!)
        }
    }
}