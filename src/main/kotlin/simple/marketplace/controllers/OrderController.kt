package simple.marketplace.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Inject
import jakarta.validation.Valid
import simple.marketplace.core.errors.ApiException
import simple.marketplace.core.errors.ID_POST_ERR
import simple.marketplace.entities.Order
import simple.marketplace.services.OrderService

@ExecuteOn(TaskExecutors.IO)
@Controller("/orders")
open class OrderController {

    @Inject
    private lateinit var service: OrderService

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    open fun create(@Body @Valid order: Order): HttpResponse<Order> {
        if (order.id != null) {
            throw ApiException(ID_POST_ERR, HttpStatus.BAD_REQUEST)
        }
        val created = service.create(order)
        return HttpResponse.ok(created)
    }
}