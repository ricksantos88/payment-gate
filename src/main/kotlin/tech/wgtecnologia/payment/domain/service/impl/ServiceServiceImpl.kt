package tech.wgtecnologia.payment.domain.service.impl

import tech.wgtecnologia.payment.domain.model.Service
import tech.wgtecnologia.payment.domain.repository.ServiceRepository
import tech.wgtecnologia.payment.domain.service.ServiceService

class ServiceServiceImpl(
    private val serviceRepository: ServiceRepository
): ServiceService {
    override fun createService(service: Service): Service {
        return serviceRepository.save(service)
    }

    override fun getServiceById(id: String): Service =
        serviceRepository.findById(id) ?: throw RuntimeException("Service not found")

    override fun getAllServices(): List<Service> = serviceRepository.findAll()

    override fun updateService(service: Service): Service {
        // TODO: Verify if the service exists before updating
        service.id?.let { getServiceById(it) }
        return serviceRepository.update(service)
    }

    override fun deleteService(id: String) {
        serviceRepository.delete(id)
    }

    override fun getServicesByCustomerId(customerId: String): List<Service> {
        return serviceRepository.findByCustomerId(customerId)
    }

}