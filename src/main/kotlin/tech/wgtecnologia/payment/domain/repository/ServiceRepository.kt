package tech.wgtecnologia.payment.domain.repository

import tech.wgtecnologia.payment.domain.model.Service
import java.util.*

interface ServiceRepository {
    fun save(service: Service): Service
    fun findById(id: String): Service?
    fun findAll(): List<Service>
    fun update(service: Service): Service
    fun delete(id: String)
    fun findByCustomerId(customerId: String): List<Service>
    fun findByExternalId(externalId: String): Service?
}