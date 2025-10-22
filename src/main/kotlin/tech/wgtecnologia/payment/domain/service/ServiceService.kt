package tech.wgtecnologia.payment.domain.service

import tech.wgtecnologia.payment.domain.model.Service
import tech.wgtecnologia.payment.domain.repository.ServiceRepository

interface ServiceService{

    fun createService(service: Service): Service
    fun getServiceById(id: String): Service?
    fun getAllServices(): List<Service>
    fun updateService(service: Service): Service
    fun deleteService(id: String)
    fun getServicesByCustomerId(customerId: String): List<Service>
}