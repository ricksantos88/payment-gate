package tech.wgtecnologia.payment.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tech.wgtecnologia.payment.application.exception.CreateUserAdminNotPermittedException
import tech.wgtecnologia.payment.application.exception.EmailAlreadyExistsException
import tech.wgtecnologia.payment.application.exception.RoleNotFoundException
import tech.wgtecnologia.payment.application.exception.UserAlreadyExistsException
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.repository.RoleRepository
import tech.wgtecnologia.payment.domain.repository.UserRepository
import java.util.UUID

class CreateAdminUserUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private val roleRepository = mockk<RoleRepository>()

    @Test
    fun `should create admin user successfully`() {
        val useCase = CreateAdminUserUseCase(userRepository, roleRepository, createAdminPermitted = true)
        val input = CreateAdminUserInput("admin", "admin@example.com", "encodedPass")
        val adminRole = Role(id = UUID.randomUUID(), name = "ROLE_ADMIN")
        val savedUser = User(
            id = UUID.randomUUID(),
            username = "admin",
            email = "admin@example.com",
            password = "encodedPass",
            roles = setOf(adminRole)
        )

        every { userRepository.existsByUsername("admin") } returns false
        every { userRepository.existsByEmail("admin@example.com") } returns false
        every { roleRepository.findByName("ROLE_ADMIN") } returns adminRole
        every { userRepository.save(any()) } returns savedUser

        val result = useCase.execute(input)

        assertEquals("admin", result.username)
        verify(exactly = 1) { userRepository.save(any()) }
    }

    @Test
    fun `should throw CreateUserAdminNotPermittedException when not permitted`() {
        val useCase = CreateAdminUserUseCase(userRepository, roleRepository, createAdminPermitted = false)
        val input = CreateAdminUserInput("admin", "admin@example.com", "encodedPass")

        assertThrows<CreateUserAdminNotPermittedException> {
            useCase.execute(input)
        }
    }

    @Test
    fun `should throw UserAlreadyExistsException when username is taken`() {
        val useCase = CreateAdminUserUseCase(userRepository, roleRepository, createAdminPermitted = true)
        val input = CreateAdminUserInput("admin", "admin@example.com", "encodedPass")

        every { userRepository.existsByUsername("admin") } returns true

        assertThrows<UserAlreadyExistsException> {
            useCase.execute(input)
        }
    }

    @Test
    fun `should throw EmailAlreadyExistsException when email is taken`() {
        val useCase = CreateAdminUserUseCase(userRepository, roleRepository, createAdminPermitted = true)
        val input = CreateAdminUserInput("admin", "admin@example.com", "encodedPass")

        every { userRepository.existsByUsername("admin") } returns false
        every { userRepository.existsByEmail("admin@example.com") } returns true

        assertThrows<EmailAlreadyExistsException> {
            useCase.execute(input)
        }
    }

    @Test
    fun `should throw RoleNotFoundException when admin role does not exist`() {
        val useCase = CreateAdminUserUseCase(userRepository, roleRepository, createAdminPermitted = true)
        val input = CreateAdminUserInput("admin", "admin@example.com", "encodedPass")

        every { userRepository.existsByUsername("admin") } returns false
        every { userRepository.existsByEmail("admin@example.com") } returns false
        every { roleRepository.findByName("ROLE_ADMIN") } returns null

        assertThrows<RoleNotFoundException> {
            useCase.execute(input)
        }
    }
}
