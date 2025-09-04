package com.example.crud.CRUD.application.entity


import jakarta.persistence.*
import java.io.Serializable

/**
 * User JPA entity mapping to users table.
 */
@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(nullable = false, length = 120, unique = true)
    var email: String,

    @Column(nullable = false)
    var age: Int
) : Serializable
