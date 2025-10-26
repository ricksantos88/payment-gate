package tech.wgtecnologia.payment.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id: UUID? = null,
    val username: String,
    val email: String,
    val password: String,
    val roles: Set<Role> = emptySet(),
    val enabled: Boolean = true,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)