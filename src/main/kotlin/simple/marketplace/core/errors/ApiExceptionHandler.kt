package simple.marketplace.core.errors

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Singleton
class ApiExceptionHandler : ExceptionHandler<ApiException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, exception: ApiException?): HttpResponse<Any> {
        if (exception == null) {
            return HttpResponse
                .serverError(ApiError("An unexpected error happened when serving the request"))
        }
        return resolveErrCode(exception.errCode, exception.msg)
    }

    private fun resolveErrCode(status: HttpStatus, msg: String): HttpResponse<Any> {
        return when (status) {
            HttpStatus.BAD_REQUEST -> {
                HttpResponse.badRequest(ApiError(msg))
            }

            HttpStatus.NOT_FOUND -> {
                HttpResponse.notFound(ApiError(msg))
            }

            else -> {
                HttpResponse.serverError(ApiError(msg))
            }
        }
    }
}