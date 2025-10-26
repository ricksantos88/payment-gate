package tech.wgtecnologia.payment.infrastructure.persistence.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import tech.wgtecnologia.payment.infrastructure.persistence.entity.UserEntity
import java.util.UUID

interface UserJpaRepository : JpaRepository<UserEntity, UUID> {
    fun findByUsername(username: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}