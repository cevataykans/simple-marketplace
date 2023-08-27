package simple.marketplace.services

import io.micronaut.http.HttpStatus
import jakarta.inject.Inject
import jakarta.inject.Singleton
import simple.marketplace.core.errors.ApiException
import simple.marketplace.core.errors.ID_NOT_FOUND_ERR
import simple.marketplace.core.errors.ID_POST_ERR
import simple.marketplace.core.errors.INVALID_ID_ERR
import simple.marketplace.entities.Product
import simple.marketplace.repositories.ProductRepository

@Singleton
class ProductService {

    @Inject
    private lateinit var productRepository: ProductRepository

    fun create(product: Product): Product {
        if (product.id != null) {
            throw ApiException(ID_POST_ERR, HttpStatus.BAD_REQUEST)
        }
        return productRepository.save(product)
    }

    fun update(product: Product): Product {
        if (product.id == null) {
            throw ApiException(INVALID_ID_ERR, HttpStatus.BAD_REQUEST)
        }

        if (!productRepository.existsById(product.id)) {
            throw ApiException(ID_NOT_FOUND_ERR, HttpStatus.NOT_FOUND)
        }

        return productRepository.update(product)
    }

    fun delete(id: Long) {
        productRepository.deleteById(id)
    }

    fun getAll(): List<Product> {
        return productRepository.findAll()
    }

    fun getById(id: Long): Product {
        val res = productRepository.findById(id)
        if (res.isEmpty) {
            throw ApiException(ID_NOT_FOUND_ERR, HttpStatus.NOT_FOUND)
        }
        return res.get()
    }

    fun exists(id: Long?): Boolean {
        if (id == null) {
            return false
        }
        return productRepository.existsById(id)
    }
}