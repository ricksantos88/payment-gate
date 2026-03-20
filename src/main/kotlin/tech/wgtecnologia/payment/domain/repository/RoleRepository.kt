package tech.wgtecnologia.payment.domain.repository

import tech.wgtecnologia.payment.domain.model.Role

interface RoleRepository {
    fun findByName(name: String): Role?
    fun findAll(): List<Role>
}
