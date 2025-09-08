package com.example.crud.CRUD.application.repository

import com.example.crud.CRUD.application.entity.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.dao.DataIntegrityViolationException

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should save user and find by id`() {
        // Given
        val user = User(name = "John Doe", email = "john.doe@example.com", age = 30)
        val savedUser = entityManager.persistAndFlush(user)

        // When
        val foundUser = userRepository.findById(savedUser.id!!)

        // Then
        assertTrue(foundUser.isPresent)
        assertEquals(savedUser.email, foundUser.get().email)
    }

    @Test
    fun `should return true for existing email`() {
        // Given
        val user = User(name = "John Doe", email = "john.doe@example.com", age = 30)
        entityManager.persistAndFlush(user)

        // When
        val exists = userRepository.existsByEmail("john.doe@example.com")

        // Then
        assertTrue(exists)
    }

    @Test
    fun `should return false for non-existing email`() {
        // When
        val exists = userRepository.existsByEmail("no.one@example.com")

        // Then
        assertFalse(exists)
    }

    @Test
    fun `should find user by email`() {
        // Given
        val user = User(name = "John Doe", email = "john.doe@example.com", age = 30)
        entityManager.persistAndFlush(user)

        // When
        val foundUser = userRepository.findByEmail("john.doe@example.com")

        // Then
        assertTrue(foundUser.isPresent)
        assertEquals(user.email, foundUser.get().email)
    }

    @Test
    fun `should throw exception for duplicate email`() {
        // Given
        val user1 = User(name = "John Doe", email = "john.doe@example.com", age = 30)
        entityManager.persistAndFlush(user1)

        val user2 = User(name = "Jane Doe", email = "john.doe@example.com", age = 31)

        // When & Then
        assertThrows(DataIntegrityViolationException::class.java) {
            userRepository.saveAndFlush(user2)
        }
    }
}
