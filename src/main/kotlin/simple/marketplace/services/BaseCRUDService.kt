package simple.marketplace.services

import io.micronaut.data.repository.CrudRepository
import io.micronaut.http.HttpStatus
import jakarta.inject.Inject
import simple.marketplace.core.errors.ApiException
import simple.marketplace.core.errors.ID_NOT_FOUND_ERR
import simple.marketplace.core.errors.ID_POST_ERR
import simple.marketplace.core.errors.INVALID_ID_ERR
import simple.marketplace.entities.Entity

abstract class BaseCRUDService<T : Entity, K : CrudRepository<T, Long>> {

    @Inject
    private lateinit var repository: K

    fun create(toCreate: T): T {

        if (toCreate.id != null) {
            throw ApiException(ID_POST_ERR, HttpStatus.BAD_REQUEST)
        }
        createConstraints(toCreate)
        return repository.save(toCreate)
    }

    fun update(toUpdate: T): T {

        if (toUpdate.id == null) {
            throw ApiException(INVALID_ID_ERR, HttpStatus.BAD_REQUEST)
        }

        if (!repository.existsById(toUpdate.id)) {
            throw ApiException(ID_NOT_FOUND_ERR, HttpStatus.NOT_FOUND)
        }

        updateConstraints(toUpdate)
        return repository.update(toUpdate)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }

    fun getAll(): List<T> {
        return repository.findAll()
    }

    fun getById(id: Long): T {
        val res = repository.findById(id)
        if (res.isEmpty) {
            throw ApiException(ID_NOT_FOUND_ERR, HttpStatus.NOT_FOUND)
        }
        return res.get()
    }

    abstract fun createConstraints(entity: T)
    abstract fun updateConstraints(entity: T)
}