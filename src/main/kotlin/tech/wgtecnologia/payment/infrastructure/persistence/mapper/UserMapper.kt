package tech.wgtecnologia.payment.infrastructure.persistence.mapper

import org.springframework.stereotype.Component
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.infrastructure.persistence.entity.RoleEntity
import tech.wgtecnologia.payment.infrastructure.persistence.entity.UserEntity

@Component
class UserMapper {

    fun toEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            username = user.username,
            email = user.email,
            password = user.password,
            enabled = user.enabled,
            roles = user.roles.map { toRoleEntity(it) }.toSet(),
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }

    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            username = entity.username,
            email = entity.email,
            password = entity.password,
            enabled = entity.enabled,
            roles = entity.roles.map { toRole(it) }.toSet(),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    private fun toRoleEntity(role: Role): RoleEntity {
        return RoleEntity(
            id = role.id,
            name = role.name,
            description = role.description,
            permissions = role.permissions
        )
    }

    private fun toRole(entity: RoleEntity): Role {
        return Role(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            permissions = entity.permissions
        )
    }
}