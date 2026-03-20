package tech.wgtecnologia.payment.domain.usecase

import tech.wgtecnologia.payment.application.exception.EmailAlreadyExistsException
import tech.wgtecnologia.payment.application.exception.RoleNotFoundException
import tech.wgtecnologia.payment.application.exception.UserAlreadyExistsException
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.repository.RoleRepository
import tech.wgtecnologia.payment.domain.repository.UserRepository

data class RegisterUserInput(
    val username: String,
    val email: String,
    val encodedPassword: String
)

class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {

    fun execute(input: RegisterUserInput): User {
        if (userRepository.existsByUsername(input.username)) {
            throw UserAlreadyExistsException("Username already exists")
        }
        if (userRepository.existsByEmail(input.email)) {
            throw EmailAlreadyExistsException("Email already exists")
        }

        val userRole = roleRepository.findByName("ROLE_USER")
            ?: throw RoleNotFoundException("Default user role not found")

        val user = User(
            username = input.username,
            email = input.email,
            password = input.encodedPassword,
            roles = setOf(userRole)
        )

        return userRepository.save(user)
    }
}
