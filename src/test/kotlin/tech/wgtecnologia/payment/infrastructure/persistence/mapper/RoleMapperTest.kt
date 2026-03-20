package tech.wgtecnologia.payment.infrastructure.persistence.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.infrastructure.persistence.entity.RoleEntity

class RoleMapperTest {

    @Test
    fun `should map Role to RoleEntity and back to Role`() {
        val role = Role(
            id = java.util.UUID.randomUUID(),
            name = "ROLE_USER",
            description = "Standard user role",
            permissions = emptySet()
        )

        val roleMapper = RoleMapper()
        val roleEntity = roleMapper.toEntity(role)
        val mappedRole = roleMapper.toDomain(roleEntity)
        assertEquals(role, mappedRole)
    }

    @Test
    fun `should mapper RoleEntity to Role`() {
        val roleEntity = RoleEntity(
            id = java.util.UUID.randomUUID(),
            name = "ROLE_ADMIN",
            description = "Admin user role",
            permissions = emptySet()
        )

        val roleMapper = RoleMapper()
        val mappedRole = roleMapper.toDomain(roleEntity)
        assertEquals(roleEntity.id, mappedRole.id)
        assertEquals(roleEntity.name, mappedRole.name)
        assertEquals(roleEntity.description, mappedRole.description)
        assertEquals(roleEntity.permissions, mappedRole.permissions)
    }

    @Test
    fun `should mapper UserRoleEntity to UserRole`() {
        val role = Role(
            id = java.util.UUID.randomUUID(),
            name = "ROLE_ADMIN",
            description = "Admin user role",
            permissions = emptySet()
        )

        val roleMapper = RoleMapper()
        val roleEntity = roleMapper.toEntity(role)
        assertEquals(role.id, roleEntity.id)
        assertEquals(role.name, roleEntity.name)
        assertEquals(role.description, roleEntity.description)
        assertEquals(role.permissions, roleEntity.permissions)
    }

}