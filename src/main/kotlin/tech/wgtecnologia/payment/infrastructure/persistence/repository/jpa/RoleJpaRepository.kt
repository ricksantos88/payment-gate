package tech.wgtecnologia.payment.infrastructure.persistence.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.wgtecnologia.payment.infrastructure.persistence.entity.RoleEntity
import java.util.*

@Repository
interface RoleJpaRepository : JpaRepository<RoleEntity, UUID> {
    fun findByName(name: String): RoleEntity?
}