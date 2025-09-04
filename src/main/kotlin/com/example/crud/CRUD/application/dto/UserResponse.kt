package com.example.crud.CRUD.application.dto


/**
 * DTO for outgoing user responses (sent to clients).
 */
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int
)
