package tech.wgtecnologia.payment.infrastructure.persistence.mapper

import org.springframework.stereotype.Component
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.infrastructure.persistence.entity.RoleEntity

@Component
class RoleMapper {

    fun toDomain(entity: RoleEntity): Role {
        return Role(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            permissions = entity.permissions
        )
    }

    fun toEntity(role: Role): RoleEntity {
        return RoleEntity(
            id = role.id,
            name = role.name,
            description = role.description,
            permissions = role.permissions
        )
    }
}