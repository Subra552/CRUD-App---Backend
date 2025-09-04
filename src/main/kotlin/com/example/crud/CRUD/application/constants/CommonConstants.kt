package com.example.crud.CRUD.application.constants

/**
 * Application-wide constants.
 */
object CommonConstants {
    // API base paths
    const val API_BASE = "/api"
    const val USER_PATH = "$API_BASE/users"

    // Messages & error codes
    const val ERR_USER_NOT_FOUND = "USER_NOT_FOUND"
    const val ERR_VALIDATION = "VALIDATION_ERROR"
    const val ERR_INTERNAL = "INTERNAL_SERVER_ERROR"

    // Validation messages
    const val MSG_NOT_BLANK = "must not be blank"
    const val MSG_INVALID_EMAIL = "must be a well-formed email address"
    const val MSG_MIN_AGE = "must be at least %d"

    // Paging defaults
    const val DEFAULT_PAGE = 0
    const val DEFAULT_SIZE = 20

    // Date format
    const val TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
}
