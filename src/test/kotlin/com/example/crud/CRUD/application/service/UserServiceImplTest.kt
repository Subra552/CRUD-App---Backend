package com.example.crud.CRUD.application.service

import com.example.crud.CRUD.application.dto.UserRequest
import com.example.crud.CRUD.application.entity.User
import com.example.crud.CRUD.application.exception.UserNotFoundException
import com.example.crud.CRUD.application.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserServiceImpl

    @Test
    fun `should create user when email is unique`() {
        // Given
        val request = UserRequest("John Doe", "john.doe@example.com", 30)
        val user = User(1, "John Doe", "john.doe@example.com", 30)

        Mockito.`when`(userRepository.existsByEmail(anyString())).thenReturn(false)
        Mockito.`when`(userRepository.save(any(User::class.java))).thenReturn(user)

        // When
        val response = userService.createUser(request)

        // Then
        assertNotNull(response)
        assertEquals(user.id, response.id)
        Mockito.verify(userRepository).save(any(User::class.java))
    }

    @Test
    fun `should throw exception when creating user with duplicate email`() {
        // Given
        val request = UserRequest("John Doe", "john.doe@example.com", 30)
        Mockito.`when`(userRepository.existsByEmail(anyString())).thenReturn(true)

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            userService.createUser(request)
        }
        Mockito.verify(userRepository, Mockito.never()).save(any(User::class.java))
    }

    @Test
    fun `should get all users`() {
        // Given
        val users = listOf(User(1, "John Doe", "john.doe@example.com", 30))
        Mockito.`when`(userRepository.findAll()).thenReturn(users)

        // When
        val responses = userService.getAllUsers()

        // Then
        assertEquals(1, responses.size)
    }

    @Test
    fun `should get user by id when user exists`() {
        // Given
        val user = User(1, "John Doe", "john.doe@example.com", 30)
        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        // When
        val response = userService.getUserById(1L)

        // Then
        assertNotNull(response)
        assertEquals(user.id, response.id)
    }

    @Test
    fun `should throw exception when getting user by id that does not exist`() {
        // Given
        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        // When & Then
        assertThrows(UserNotFoundException::class.java) {
            userService.getUserById(1L)
        }
    }

    @Test
    fun `should update user when user exists`() {
        // Given
        val request = UserRequest("Jane Doe", "jane.doe@example.com", 31)
        val existingUser = User(1, "John Doe", "john.doe@example.com", 30)
        val updatedUser = User(1, "Jane Doe", "jane.doe@example.com", 31)

        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.of(existingUser))
        Mockito.`when`(userRepository.existsByEmail(anyString())).thenReturn(false)
        Mockito.`when`(userRepository.save(any(User::class.java))).thenReturn(updatedUser)

        // When
        val response = userService.updateUser(1L, request)

        // Then
        assertNotNull(response)
        assertEquals("Jane Doe", response.name)
        assertEquals("jane.doe@example.com", response.email)
    }

    @Test
    fun `should throw exception when updating user that does not exist`() {
        // Given
        val request = UserRequest("Jane Doe", "jane.doe@example.com", 31)
        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        // When & Then
        assertThrows(UserNotFoundException::class.java) {
            userService.updateUser(1L, request)
        }
    }

    @Test
    fun `should delete user when user exists`() {
        // Given
        Mockito.`when`(userRepository.existsById(1L)).thenReturn(true)

        // When
        userService.deleteUser(1L)

        // Then
        Mockito.verify(userRepository).existsById(1L)
        Mockito.verify(userRepository).deleteById(1L)
    }

    @Test
    fun `should throw exception when deleting user that does not exist`() {
        // Given
        Mockito.`when`(userRepository.existsById(1L)).thenReturn(false)

        // When & Then
        assertThrows(UserNotFoundException::class.java) {
            userService.deleteUser(1L)
        }
        Mockito.verify(userRepository).existsById(1L)
        Mockito.verify(userRepository, Mockito.never()).deleteById(any(Long::class.java))
    }


}
