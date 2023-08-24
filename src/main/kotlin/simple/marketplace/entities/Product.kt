package simple.marketplace.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

const val REPOSITORY = "products"

@Serdeable
@MappedEntity(value = REPOSITORY)
data class Product(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.IDENTITY)
    var id: Long? = null,
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

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }
}