package tech.wgtecnologia.payment.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tech.wgtecnologia.payment.application.exception.EmailAlreadyExistsException
import tech.wgtecnologia.payment.application.exception.RoleNotFoundException
import tech.wgtecnologia.payment.application.exception.UserAlreadyExistsException
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.repository.RoleRepository
import tech.wgtecnologia.payment.domain.repository.UserRepository
import java.util.UUID

class RegisterUserUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private val roleRepository = mockk<RoleRepository>()
    private val useCase = RegisterUserUseCase(userRepository, roleRepository)

    @Test
    fun `should register user successfully`() {
        val input = RegisterUserInput("john", "john@example.com", "encodedPass")
        val userRole = Role(id = UUID.randomUUID(), name = "ROLE_USER")
        val savedUser = User(
            id = UUID.randomUUID(),
            username = "john",
            email = "john@example.com",
            password = "encodedPass",
            roles = setOf(userRole)
        )

        every { userRepository.existsByUsername("john") } returns false
        every { userRepository.existsByEmail("john@example.com") } returns false
        every { roleRepository.findByName("ROLE_USER") } returns userRole
        every { userRepository.save(any()) } returns savedUser

        val result = useCase.execute(input)

        assertEquals("john", result.username)
        assertEquals("john@example.com", result.email)
        verify(exactly = 1) { userRepository.save(any()) }
    }

    @Test
    fun `should throw UserAlreadyExistsException when username is taken`() {
        val input = RegisterUserInput("john", "john@example.com", "encodedPass")

        every { userRepository.existsByUsername("john") } returns true

        assertThrows<UserAlreadyExistsException> {
            useCase.execute(input)
        }
    }

    @Test
    fun `should throw EmailAlreadyExistsException when email is taken`() {
        val input = RegisterUserInput("john", "john@example.com", "encodedPass")

        every { userRepository.existsByUsername("john") } returns false
        every { userRepository.existsByEmail("john@example.com") } returns true

        assertThrows<EmailAlreadyExistsException> {
            useCase.execute(input)
        }
    }

    @Test
    fun `should throw RoleNotFoundException when user role does not exist`() {
        val input = RegisterUserInput("john", "john@example.com", "encodedPass")

        every { userRepository.existsByUsername("john") } returns false
        every { userRepository.existsByEmail("john@example.com") } returns false
        every { roleRepository.findByName("ROLE_USER") } returns null

        assertThrows<RoleNotFoundException> {
            useCase.execute(input)
        }
    }
}
