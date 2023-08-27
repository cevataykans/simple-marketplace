package simple.marketplace.core.errors

import io.micronaut.serde.annotation.Serdeable

const val ID_NOT_FOUND: String = "Id not found"
const val INVALID_ID: String = "Id is null or format is wrong"

@Serdeable
class ApiError(message: String) {

}