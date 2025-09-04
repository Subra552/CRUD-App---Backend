package com.example.crud.CRUD.application.exception


import com.example.crud.CRUD.application.constants.CommonConstants
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Global exception handler that maps exceptions to structured JSON error responses.
 */
@ControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFound(ex: UserNotFoundException, request: HttpServletRequest): ResponseEntity<ApiErrorResponse> {
        log.warn("UserNotFoundException: {}", ex.message)
        val body = ApiErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            errorCode = CommonConstants.ERR_USER_NOT_FOUND,
            message = ex.message ?: "User not found",
            details = null,
            path = request.requestURI
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        log.warn("Validation failed: {}", ex.bindingResult)
        val errors = ex.bindingResult.allErrors.associate { err ->
            if (err is FieldError) {
                err.field to (err.defaultMessage ?: "invalid")
            } else {
                err.objectName to (err.defaultMessage ?: "invalid")
            }
        }
        val body = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            errorCode = CommonConstants.ERR_VALIDATION,
            message = "Validation failed",
            details = mapOf("errors" to errors),
            path = request.requestURI
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgs(ex: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<ApiErrorResponse> {
        log.warn("IllegalArgumentException: {}", ex.message)
        val body = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            errorCode = CommonConstants.ERR_VALIDATION,
            message = ex.message ?: "Invalid argument",
            details = null,
            path = request.requestURI
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception, request: HttpServletRequest): ResponseEntity<ApiErrorResponse> {
        log.error("Unhandled exception", ex)
        val body = ApiErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errorCode = CommonConstants.ERR_INTERNAL,
            message = "An unexpected error occurred",
            details = mapOf("exception" to (ex.message ?: "N/A")),
            path = request.requestURI
        )
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
