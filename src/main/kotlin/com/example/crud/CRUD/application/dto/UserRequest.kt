package com.example.crud.CRUD.application.dto


import com.example.crud.CRUD.application.constants.CommonConstants
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

/**
 * DTO for incoming user creation/update requests.
 */
data class UserRequest(
    @field:NotBlank(message = "name ${CommonConstants.MSG_NOT_BLANK}")
    val name: String?,

    @field:NotBlank(message = "email ${CommonConstants.MSG_NOT_BLANK}")
    @field:Email(message = "email ${CommonConstants.MSG_INVALID_EMAIL}")
    val email: String?,

    @field:Min(value = 0, message = "age must be >= 0")
    val age: Int?
)
