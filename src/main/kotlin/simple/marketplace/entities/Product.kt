package simple.marketplace.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

const val REPOSITORY = "product"

@MappedEntity
data class Product(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.IDENTITY)
    val id: Long? = null,
    var name: String,
    var description: String,
    var price: Float
) {

    override fun equals(other: Any?): Boolean {
        return (other is Product)
                && this.name == other.name
                && this.description == other.description
                && this.price == other.price
    }
}