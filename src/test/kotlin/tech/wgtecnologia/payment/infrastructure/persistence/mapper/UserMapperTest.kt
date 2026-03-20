package tech.wgtecnologia.payment.infrastructure.persistence.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.domain.model.User
import java.util.UUID

class UserMapperTest {

    @Test
    fun `should map User to UserEntity and back to User`() {
        val user = User(
            id = UUID.randomUUID(),
            username = "testuser",
            email = "",
            password = "password",
            enabled = true,
            roles = emptySet(),
            createdAt = null,
            updatedAt = null
        )

        val userMapper = UserMapper()
        val userEntity = userMapper.toEntity(user)
        val mappedUser = userMapper.toDomain(userEntity)
        assertEquals(user, mappedUser)
    }

    @Test
    fun `should map User with roles to UserEntity and back to User`() {
        val role = Role(
            id = UUID.randomUUID(),
            name = "ROLE_USER",
            description = "Standard user role",
            permissions = emptySet()
        )

        val user = User(
            id = UUID.randomUUID(),
            username = "testuser",
            email = "",
            password = "password",
            enabled = true,
            roles = setOf(role),
            createdAt = null,
            updatedAt = null
        )

        val userMapper = UserMapper()
        val userEntity = userMapper.toEntity(user)
        val mappedUser = userMapper.toDomain(userEntity)
        assertEquals(user, mappedUser)
    }

    

}