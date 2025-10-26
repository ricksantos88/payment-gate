package tech.wgtecnologia.payment.application.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tech.wgtecnologia.payment.application.dto.RegisterRequest
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.port.UserPort

import java.util.*

@Service
class UserService(
    private val userRepository: UserPort,
    private val passwordEncoder: PasswordEncoder,
    private val roleService: RoleService
) {

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun findById(id: UUID): User? {
        return userRepository.findById(id)
    }

    fun createUser(request: RegisterRequest): User {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val userRole = roleService.findByName("ROLE_USER")
            ?: throw IllegalStateException("Default user role not found")

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = setOf(userRole)
        )

        return userRepository.save(user)
    }

    fun createAdminUser(request: RegisterRequest): User {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val adminRole = roleService.findByName("ROLE_ADMIN")
            ?: throw IllegalStateException("Admin role not found")

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = setOf(adminRole)
        )

        return userRepository.save(user)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }
}