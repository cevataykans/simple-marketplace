package simple.marketplace.core.errors

import io.micronaut.http.HttpStatus
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class ApiException(val msg: String, val errCode: HttpStatus) : Exception(msg) {
}