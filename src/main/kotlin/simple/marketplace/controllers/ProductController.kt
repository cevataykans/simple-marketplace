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
import simple.marketplace.core.errors.ID_MATCH_ERR
import simple.marketplace.core.errors.ID_POST_ERR
import simple.marketplace.core.errors.INVALID_ID_ERR
import simple.marketplace.entities.Product
import simple.marketplace.services.ProductService

@ExecuteOn(TaskExecutors.IO)
@Controller("/products")
open class ProductController {

    @Inject
    private lateinit var service: ProductService

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getById(id: Long): HttpResponse<Product> {
        val found = service.getById(id)
        return HttpResponse.ok(found)
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): HttpResponse<List<Product>> {
        val all = service.getAll()
        return HttpResponse.ok(all)
    }

    //TODO: make sure that product body is a valid Product, no field (name, id, description, price) must be deserialized!
    //TODO: try making idempotent
    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    open fun create(@Body @Valid product: Product): HttpResponse<Product> {
        if (product.id != null) {
            throw ApiException(ID_POST_ERR, HttpStatus.BAD_REQUEST)
        }
        val created = service.create(product)
        return HttpResponse.ok(created)
    }

    //TODO: make sure that product body is a valid Product, no field (name, id, description, price) must be deserialized!
    @Put("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    open fun update(id: Long, @Body @Valid product: Product): HttpResponse<Product> {
        if (product.id == null) {
            throw ApiException(INVALID_ID_ERR, HttpStatus.BAD_REQUEST)
        }

        if (id != product.id) {
            throw ApiException(ID_MATCH_ERR, HttpStatus.BAD_REQUEST)
        }
        return HttpResponse.ok(service.update(product))
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: Long) {
        service.delete(id)
    }
}