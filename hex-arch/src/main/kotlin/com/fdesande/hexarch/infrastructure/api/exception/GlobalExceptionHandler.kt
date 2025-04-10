package com.fdesande.hexarch.infrastructure.api.exception

import com.fdesande.hexarch.infrastructure.exception.ApplicationException
import com.fdesande.hexarch.infrastructure.exception.EmptyResultException
import com.fdesande.hexarch.infrastructure.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EmptyResultException::class)
    @Suppress("unused")
    fun handleEmptyResultException(
        ex: ApplicationException,
        request: WebRequest
    ): ResponseEntity<List<Any>> {
        return ResponseEntity.status(getHttpStatus(ex)).body(emptyList<Any>())
    }

    @ExceptionHandler(ApplicationException::class)
    @Suppress("unused")
    fun handleApplicationException(
        ex: ApplicationException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        return createErrorResponse(
            status = getHttpStatus(ex),
            message = ex.message,
            error = ex.code
        )
    }

    @ExceptionHandler(Exception::class)
    @Suppress("unused")
    fun handleGenericException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        return createErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = ex.message,
        )
    }

    private fun createErrorResponse(
        status: HttpStatus,
        message: String?,
        error: String = ""
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = status.value(),
            error = error,
            message = message ?: "An unexpected error occurred",
        )
        return ResponseEntity(errorResponse, status)
    }

    private fun getHttpStatus(ex: ApplicationException): HttpStatus =
        when (ex) {
            is EmptyResultException -> HttpStatus.OK
            is ResourceNotFoundException -> HttpStatus.NOT_FOUND
        }
}
