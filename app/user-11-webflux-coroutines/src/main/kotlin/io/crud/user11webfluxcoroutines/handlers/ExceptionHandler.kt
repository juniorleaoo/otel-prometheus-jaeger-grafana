package io.crud.user11webfluxcoroutines.handlers

import io.crud.user11webfluxcoroutines.exception.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import javax.validation.ConstraintViolationException

data class ErrorsResponse(
    var errors: MutableSet<String>
) {
    constructor() : this(mutableSetOf())
    constructor(error: String) : this(mutableSetOf(error))
}

@ControllerAdvice
@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(WebExchangeBindException::class)
    fun webExchangeBindException(exception: WebExchangeBindException): ResponseEntity<ErrorsResponse> {
        val response = ErrorsResponse(mutableSetOf())
        response.errors.apply {
            addAll(exception.bindingResult.allErrors.map { it.defaultMessage ?: "" })
        }
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(exception: NotFoundException): ResponseEntity<ErrorsResponse> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(exception: ConstraintViolationException): ResponseEntity<ErrorsResponse> {
        val response = ErrorsResponse()
        response.errors.apply {
            addAll(exception.constraintViolations.map { it.message })
        }
        return ResponseEntity.badRequest().body(response)
    }

}