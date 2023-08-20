package simple.marketplace.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.MappedEntity


@MappedEntity
data class Product(
        @field:Id
        @GeneratedValue
        val id: String,
        var name: String,
        var description: String,
        var price: Double) {
}