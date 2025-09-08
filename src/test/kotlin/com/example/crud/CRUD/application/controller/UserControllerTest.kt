package com.example.crud.CRUD.application.controller

import com.example.crud.CRUD.application.dto.UserRequest
import com.example.crud.CRUD.application.dto.UserResponse
import com.example.crud.CRUD.application.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertNull

import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class UserControllerTest {

    private lateinit var userService: UserService
    private lateinit var userController: UserController

    @BeforeEach
    fun setup() {
        userService = mock(UserService::class.java)
        userController = UserController(userService)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> anySafe(type: Class<T>): T {
        Mockito.any<T>(type)
        return null as T
    }


    @Test
    fun `createUser should return created user`() {
        // Given
        val request = UserRequest(name = "John", email = "john@example.com", age = 30)
        val response = UserResponse(id = 1L, name = "John", email = "john@example.com", age = 30)

        // Simple approach without matchers
        `when`(userService.createUser(request)).thenReturn(response)

        // When
        val result: ResponseEntity<UserResponse> = userController.createUser(request)

        // Then
        assertEquals(200, result.statusCode.value())
        assertEquals(response, result.body)
        verify(userService).createUser(request)
    }


    @Test
    fun `getAllUsers should return list of users`() {
        val users = listOf(
            UserResponse(1L, "John", "john@example.com", age = 30),
            UserResponse(2L, "Jane", "jane@example.com", age = 32)
        )

        `when`(userService.getAllUsers()).thenReturn(users)

        val result: ResponseEntity<List<UserResponse>> = userController.getAllUsers()

        assertEquals(200, result.statusCode.value())
        assertEquals(2, result.body?.size)
        assertEquals("Jane", result.body?.get(1)?.name)
        verify(userService, times(1)).getAllUsers()
    }

    @Test
    fun `getUserById should return single user`() {
        val user = UserResponse(1L, "John", "john@example.com", age = 30)

        `when`(userService.getUserById(eq(1L))).thenReturn(user)

        val result: ResponseEntity<UserResponse> = userController.getUserById(1L)

        assertEquals(200, result.statusCode.value())
        assertEquals(user, result.body)
        verify(userService, times(1)).getUserById(1L)
    }

    @Test
    fun `updateUser should return updated user`() {
        // Given
        val request = UserRequest(name = "John Updated", email = "john.updated@example.com", age = 31)
        val response = UserResponse(1L, "John Updated", "john.updated@example.com", age = 31)
        val userId = 1L

        // Mock the service behavior
        `when`(userService.updateUser(userId, request)).thenReturn(response)

        // When
        val result: ResponseEntity<UserResponse> = userController.updateUser(userId, request)

        // Then
        assertEquals(200, result.statusCode.value())
        assertEquals(response, result.body)
        verify(userService).updateUser(userId, request)
    }

    @Test
    fun `deleteUser should return no content`() {
        doNothing().`when`(userService).deleteUser(1L)

        val result: ResponseEntity<Void> = userController.deleteUser(1L)

        assertEquals(204, result.statusCode.value())
        assertNull(result.body)
        verify(userService, times(1)).deleteUser(1L)
    }
}
