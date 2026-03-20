package tech.wgtecnologia.payment.application.dto

import java.util.UUID

data class UserResponse(
    val id: UUID,
    val username: String,
    val email: String,
    val roles: Set<String>
)
