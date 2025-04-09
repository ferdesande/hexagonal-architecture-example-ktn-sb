package com.fdesande.hexarch.infrastructure.exception

sealed class ApplicationException(
    message: String,
    cause: Throwable?,
    val code: String,
) : RuntimeException(message, cause)

class ResourceNotFoundException(
    message: String,
    cause: Throwable? = null,
    code: String = "NOT_FOUND",
) : ApplicationException(message, cause, code)

class EmptyResultException(
    message: String,
    cause: Throwable? = null,
    code: String = "EMPTY_RESULT",
) : ApplicationException(message, cause, code)
