package tech.wgtecnologia.payment.infrastructure.persistence.repository

import tech.wgtecnologia.payment.domain.model.User
import java.util.UUID

interface UserRepository {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun findById(id: UUID): User?
    fun save(user: User): User
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findAll(): List<User>
}