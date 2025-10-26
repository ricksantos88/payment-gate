package tech.wgtecnologia.payment.application.service

import org.springframework.stereotype.Service
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.infrastructure.persistence.repository.jpa.RoleJpaRepository

@Service
class RoleService(
    private val roleJpaRepository: RoleJpaRepository
) {

    fun findByName(name: String): Role? {
        return roleJpaRepository.findByName(name)?.let { entity ->
            Role(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                permissions = entity.permissions
            )
        }
    }

    fun findAll(): List<Role> {
        return roleJpaRepository.findAll().map { entity ->
            Role(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                permissions = entity.permissions
            )
        }
    }
}