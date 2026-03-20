package tech.wgtecnologia.payment.domain.usecase

import tech.wgtecnologia.payment.application.exception.CreateUserAdminNotPermittedException
import tech.wgtecnologia.payment.application.exception.EmailAlreadyExistsException
import tech.wgtecnologia.payment.application.exception.RoleNotFoundException
import tech.wgtecnologia.payment.application.exception.UserAlreadyExistsException
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.repository.RoleRepository
import tech.wgtecnologia.payment.domain.repository.UserRepository

data class CreateAdminUserInput(
    val username: String,
    val email: String,
    val encodedPassword: String
)

class CreateAdminUserUseCase(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val createAdminPermitted: Boolean
) {

    fun execute(input: CreateAdminUserInput): User {
        if (!createAdminPermitted) {
            throw CreateUserAdminNotPermittedException("Creating admin users is not permitted")
        }
        if (userRepository.existsByUsername(input.username)) {
            throw UserAlreadyExistsException("Username already exists")
        }
        if (userRepository.existsByEmail(input.email)) {
            throw EmailAlreadyExistsException("Email already exists")
        }

        val adminRole = roleRepository.findByName("ROLE_ADMIN")
            ?: throw RoleNotFoundException("Admin role not found")

        val user = User(
            username = input.username,
            email = input.email,
            password = input.encodedPassword,
            roles = setOf(adminRole)
        )

        return userRepository.save(user)
    }
}
