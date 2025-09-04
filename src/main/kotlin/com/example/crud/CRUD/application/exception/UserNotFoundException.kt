package com.example.crud.CRUD.application.exception


import org.springframework.http.HttpStatus

/**
 * Thrown when a user with a given id is not found.
 */
class UserNotFoundException(message: String) : RuntimeException(message)
