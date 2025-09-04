package com.example.crud.CRUD.application.exception


import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * Standard API error response returned to clients.
 */
data class ApiErrorResponse(
    val status: Int,
    val errorCode: String,
    val message: String,
    val details: Map<String, Any>? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    val path: String? = null
)
