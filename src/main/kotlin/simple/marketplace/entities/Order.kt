package simple.marketplace.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.PositiveOrZero

const val ORDER_REPOSITORY = "orders"

@Serdeable
@MappedEntity(value = ORDER_REPOSITORY)
data class Order(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.IDENTITY)
    var id: Long? = null,
    var product_id: Long,
    @PositiveOrZero
    var quantity: Int
) {
    override fun equals(other: Any?): Boolean {
        return (other is Order)
                && this.id == other.id
                && this.product_id == other.product_id
                && this.quantity == other.quantity
    }
}