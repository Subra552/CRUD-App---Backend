package com.example.crud.CRUD.application.mapper

import com.example.crud.CRUD.application.dto.UserRequest
import com.example.crud.CRUD.application.dto.UserResponse
import com.example.crud.CRUD.application.entity.User

/**
 * Manual mapper between User entity and DTOs.
 */
object UserMapper {

    fun toEntity(req: UserRequest): User =
        User(name = req.name!!.trim(), email = req.email!!.trim(), age = req.age!!)

    fun toResponse(entity: User): UserResponse =
        UserResponse(id = entity.id!!, name = entity.name, email = entity.email, age = entity.age)

    fun updateEntity(entity: User, req: UserRequest): User {
        req.name?.let { entity.name = it.trim() }
        req.email?.let { entity.email = it.trim() }
        req.age?.let { entity.age = it }
        return entity
    }
}
