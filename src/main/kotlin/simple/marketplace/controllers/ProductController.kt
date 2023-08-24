package simple.marketplace.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import simple.marketplace.entities.Product
import simple.marketplace.services.ProductService

@ExecuteOn(TaskExecutors.IO)
@Controller("/products")
open class ProductController(var service: ProductService) {

    @Get("/{id}")
    fun getById(id: Long): HttpResponse<Product> {
        val found = service.getById(id)
        return HttpResponse.ok(found)
    }
}