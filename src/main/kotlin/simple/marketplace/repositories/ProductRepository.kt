package simple.marketplace.repositories

import io.micronaut.context.annotation.Executable
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

import simple.marketplace.entities.Product

@MongoRepository
interface ProductRepository : CrudRepository<Product, ObjectId> {
}