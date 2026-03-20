package tech.wgtecnologia.payment.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.repository.UserRepository
import java.util.UUID

class GetAllUsersUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private val useCase = GetAllUsersUseCase(userRepository)

    @Test
    fun `should return all users`() {
        val users = listOf(
            User(id = UUID.randomUUID(), username = "alice", email = "alice@example.com", password = "pass"),
            User(id = UUID.randomUUID(), username = "bob", email = "bob@example.com", password = "pass")
        )

        every { userRepository.findAll() } returns users

        val result = useCase.execute()

        assertEquals(2, result.size)
        assertEquals("alice", result[0].username)
        assertEquals("bob", result[1].username)
    }

    @Test
    fun `should return empty list when no users exist`() {
        every { userRepository.findAll() } returns emptyList()

        val result = useCase.execute()

        assertEquals(0, result.size)
    }
}
