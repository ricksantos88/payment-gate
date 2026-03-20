package tech.wgtecnologia.payment.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import tech.wgtecnologia.payment.domain.model.Role
import tech.wgtecnologia.payment.domain.repository.RoleRepository
import tech.wgtecnologia.payment.infrastructure.persistence.mapper.RoleMapper
import tech.wgtecnologia.payment.infrastructure.persistence.repository.jpa.RoleJpaRepository

@Repository
class RoleRepositoryImpl(
    private val roleJpaRepository: RoleJpaRepository,
    private val roleMapper: RoleMapper
) : RoleRepository {

    override fun findByName(name: String): Role? {
        return roleJpaRepository.findByName(name)?.let(roleMapper::toDomain)
    }

    override fun findAll(): List<Role> {
        return roleJpaRepository.findAll().map(roleMapper::toDomain)
    }
}
