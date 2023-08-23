package simple.marketplace.services

import jakarta.inject.Inject
import jakarta.inject.Singleton
import simple.marketplace.entities.Product
import simple.marketplace.repositories.ProductRepository

@Singleton
class ProductService {

    @Inject
    private lateinit var productRepository: ProductRepository

    fun create(product: Product): Product {
        if (product.id != null) {
            throw Exception("id cannot be set")
        }
        return productRepository.save(product)
    }

    fun update(product: Product): Product {
        if (product.id == null) {
            throw Exception("id must be set")
        }

        //TODO: check if product exists in db, if not, throw bad req error

        return productRepository.save(product)
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
            throw RuntimeException("Product not found")
        }
        return res.get()
    }
}