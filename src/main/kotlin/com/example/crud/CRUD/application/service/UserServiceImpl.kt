package com.example.crud.CRUD.application.service


import com.example.crud.CRUD.application.dto.UserRequest
import com.example.crud.CRUD.application.dto.UserResponse
import com.example.crud.CRUD.application.entity.User
import com.example.crud.CRUD.application.exception.UserNotFoundException
import com.example.crud.CRUD.application.mapper.UserMapper
import com.example.crud.CRUD.application.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Implementation of UserService with START/END logging.
 */
@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Transactional
    override fun createUser(request: UserRequest): UserResponse {
        log.info("START createUser - request={}", request)
        if (userRepository.existsByEmail(request.email!!.trim())) {
            // Could create a custom DuplicateEmailException; for brevity throw RuntimeException
            val msg = "Email already exists: ${request.email}"
            log.warn("END createUser - failed duplicate email - {}", msg)
            throw IllegalArgumentException(msg)
        }
        val saved: User = userRepository.save(UserMapper.toEntity(request))
        val response = UserMapper.toResponse(saved)
        log.info("END createUser - response={}", response)
        return response
    }

    @Transactional(readOnly = true)
    override fun getAllUsers(): List<UserResponse> {
        log.info("START getAllUsers")
        val responses = userRepository.findAll().map(UserMapper::toResponse)
        log.info("END getAllUsers - count={}", responses.size)
        return responses
    }

    @Transactional(readOnly = true)
    override fun getUserById(id: Long): UserResponse {
        log.info("START getUserById - id={}", id)
        val user = userRepository.findById(id).orElseThrow {
            log.warn("getUserById - not found id={}", id)
            UserNotFoundException("User not found with id=$id")
        }
        val response = UserMapper.toResponse(user)
        log.info("END getUserById - response={}", response)
        return response
    }

    @Transactional
    override fun updateUser(id: Long, request: UserRequest): UserResponse {
        log.info("START updateUser - id={}, request={}", id, request)
        val existing = userRepository.findById(id).orElseThrow {
            log.warn("updateUser - not found id={}", id)
            UserNotFoundException("User not found with id=$id")
        }

        // if email changes, ensure uniqueness
        val newEmail = request.email?.trim()
        if (newEmail != null && newEmail != existing.email && userRepository.existsByEmail(newEmail)) {
            val msg = "Email already exists: $newEmail"
            log.warn("END updateUser - {}", msg)
            throw IllegalArgumentException(msg)
        }

        val updated = UserMapper.updateEntity(existing, request)
        val saved = userRepository.save(updated)
        val response = UserMapper.toResponse(saved)
        log.info("END updateUser - response={}", response)
        return response
    }

    @Transactional
    override fun deleteUser(id: Long) {
        log.info("START deleteUser - id={}", id)
        if (!userRepository.existsById(id)) {
            log.warn("deleteUser - not found id={}", id)
            throw UserNotFoundException("User not found with id=$id")
        }
        userRepository.deleteById(id)
        log.info("END deleteUser - id={}", id)
    }
}
