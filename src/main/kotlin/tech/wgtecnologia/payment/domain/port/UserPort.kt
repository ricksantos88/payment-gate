package tech.wgtecnologia.payment.domain.port

import tech.wgtecnologia.payment.domain.model.User
import java.util.*


interface UserPort {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User
    fun findById(id: UUID): User
    fun save(user: User): User
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findAll(): List<User>
}