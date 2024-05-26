package io.crud.user11mvc.handlers

import io.crud.user11mvc.exception.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

data class ErrorsResponse(
    var errors: MutableSet<String>
) {
    constructor() : this(mutableSetOf())
    constructor(error: String) : this(mutableSetOf(error))
}

@ControllerAdvice
@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(exception: NotFoundException): ResponseEntity<ErrorsResponse> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArguentNotValidException(
        req: HttpServletRequest,
        exception: MethodArgumentNotValidException
    ): ResponseEntity<ErrorsResponse> {
        val response = ErrorsResponse()
        response.errors.apply {
            addAll(exception.bindingResult.allErrors.map { it.defaultMessage ?: "" })
        }
        return ResponseEntity.badRequest().body(response)
    }

}