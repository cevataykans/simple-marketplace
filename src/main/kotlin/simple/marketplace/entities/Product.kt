package simple.marketplace.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId

const val REPOSITORY = "products"

@MappedEntity(value = REPOSITORY)
data class Product(
        @field:Id
        @GeneratedValue
        val id: ObjectId,
        var name: String,
        var description: String,
        var price: Double)