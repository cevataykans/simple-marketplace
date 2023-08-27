package simple.marketplace.core.errors

import io.micronaut.serde.annotation.Serdeable

const val ID_POST_ERR: String = "ID cannot be set before resource creation"
const val ID_NOT_FOUND_ERR: String = "ID not found"
const val INVALID_ID_ERR: String = "ID is null or format is wrong"
const val ID_MATCH_ERR: String = "Received IDs do not match"

@Serdeable
class ApiError(message: String) {

}