package simple.marketplace.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import simple.marketplace.services.ProductService

@ExecuteOn(TaskExecutors.IO)
@Controller("/products")
open class ProductController(var service: ProductService) {
}