package tech.wgtecnologia.payment.application.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tech.wgtecnologia.payment.application.dto.RegisterRequest
import tech.wgtecnologia.payment.application.exception.CreateUserAdminNotPermittedException
import tech.wgtecnologia.payment.application.exception.EmailAlreadyExistsException
import tech.wgtecnologia.payment.application.exception.RoleNotFoundException
import tech.wgtecnologia.payment.application.exception.UserAlreadyExistsException
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.port.UserPort

import java.util.*

@Service
class UserService(
    private val userRepository: UserPort,
    private val passwordEncoder: PasswordEncoder,
    private val roleService: RoleService,
    @Value("\${app.create-admin-user-permitted}") private val createUserAdminPermitted: Boolean = true
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
            throw UserAlreadyExistsException("Username already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistsException("Email already exists")
        }

        val userRole = roleService.findByName("ROLE_USER")
            ?: throw RoleNotFoundException("Default user role not found")

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = setOf(userRole)
        )

        return userRepository.save(user)
    }

    fun createAdminUser(request: RegisterRequest): User {
        if (!createUserAdminPermitted) throw CreateUserAdminNotPermittedException("Creating admin users is not permitted")

        if (userRepository.existsByUsername(request.username)) {
            throw UserAlreadyExistsException("Username already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistsException("Email already exists")
        }

        val adminRole = roleService.findByName("ROLE_ADMIN")
            ?: throw RoleNotFoundException("Admin role not found")

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