package simple.marketplace.services

import io.micronaut.data.repository.CrudRepository
import jakarta.inject.Inject

abstract class BaseServiceTest<T, K : CrudRepository<T, Long>> {

    @Inject
    private lateinit var repository: K

    private var id: Int = 0
    private fun generate(): T {
        return generate(id++)
    }

    abstract fun generate(id: Int): T

    private fun generateAndSave(): T {
        val generated = generate()
        return repository.save(generated)
    }
}