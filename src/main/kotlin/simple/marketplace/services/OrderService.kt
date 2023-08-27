package simple.marketplace.services

import io.micronaut.http.HttpStatus
import jakarta.inject.Inject
import jakarta.inject.Singleton
import simple.marketplace.core.errors.ApiException
import simple.marketplace.core.errors.ID_POST_ERR
import simple.marketplace.entities.Order
import simple.marketplace.repositories.OrderRepository

@Singleton
class OrderService {

    @Inject
    private lateinit var orderRepository: OrderRepository

    fun create(order: Order): Order {
        if (order.id != null) {
            throw ApiException(ID_POST_ERR, HttpStatus.BAD_REQUEST)
        }
        return orderRepository.save(order)
    }
}