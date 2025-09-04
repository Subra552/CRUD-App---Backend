package com.example.crud.CRUD.application.controller


import com.example.crud.CRUD.application.constants.CommonConstants
import com.example.crud.CRUD.application.dto.UserRequest
import com.example.crud.CRUD.application.dto.UserResponse
import com.example.crud.CRUD.application.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * REST Controller for user CRUD operations.
 */
@RestController
@RequestMapping(CommonConstants.USER_PATH)
@Validated
@Tag(name = "User API", description = "CRUD operations for users")
class UserController(
    private val userService: UserService
) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping
    @Operation(summary = "Create a new user")
    fun createUser(@Valid @RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        log.info("START POST createUser - request={}", request)
        val response = userService.createUser(request)
        log.info("END POST createUser - response={}", response)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    @Operation(summary = "Get all users")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        log.info("START GET getAllUsers")
        val result = userService.getAllUsers()
        log.info("END GET getAllUsers - count={}", result.size)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        log.info("START GET getUserById - id={}", id)
        val response = userService.getUserById(id)
        log.info("END GET getUserById - response={}", response)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        log.info("START PUT updateUser - id={}, request={}", id, request)
        val response = userService.updateUser(id, request)
        log.info("END PUT updateUser - response={}", response)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        log.info("START DELETE deleteUser - id={}", id)
        userService.deleteUser(id)
        log.info("END DELETE deleteUser - id={}", id)
        return ResponseEntity.noContent().build()
    }
}
