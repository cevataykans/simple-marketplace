package simple.marketplace.services

import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import simple.marketplace.entities.Product
import simple.marketplace.repositories.ProductRepository
import java.util.*

@Singleton
class ProductService {

    @Inject
    lateinit var repository: ProductRepository

    fun create(product: Product) : Product {
        if (product.id != ObjectId("")) {
            throw Exception("id cannot be set")
        }
        return repository.save(product)
    }

    fun update(product: Product) : Product {
        if (product.id == ObjectId("")) {
            throw Exception("id must be set")
        }

        //TODO: check if product exists in db, if not, throw bad req error

        return repository.save(product)
    }

    fun delete(id: String) {
        repository.deleteById(ObjectId(id))
    }

    fun getAll() : List<Product> {
        return repository.findAll()
    }

    fun getById(id: String) : Product {
        var res = repository.findById(ObjectId(id))
        if (res.isEmpty) {
            throw RuntimeException("Product not found")
        }
        return res.get()
    }
}