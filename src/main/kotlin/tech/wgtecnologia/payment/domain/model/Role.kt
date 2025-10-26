package tech.wgtecnologia.payment.domain.model

import tech.wgtecnologia.payment.shared.enums.Permission
import java.util.*

data class Role(
    val id: UUID? = null,
    val name: String,
    val permissions: Set<Permission> = emptySet(),
    val description: String? = null
)
