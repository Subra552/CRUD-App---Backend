package com.example.crud.CRUD.application.service


import com.example.crud.CRUD.application.dto.UserRequest
import com.example.crud.CRUD.application.dto.UserResponse

/**
 * Service contract for user operations.
 */
interface UserService {
    fun createUser(request: UserRequest): UserResponse
    fun getAllUsers(): List<UserResponse>
    fun getUserById(id: Long): UserResponse
    fun updateUser(id: Long, request: UserRequest): UserResponse
    fun deleteUser(id: Long)
}
