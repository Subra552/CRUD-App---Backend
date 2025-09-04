package com.example.crud.CRUD.application.repository


import com.example.crud.CRUD.application.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * Repository for User entity.
 */
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Optional<User>
}
