package tech.wgtecnologia.payment.domain.service

import tech.wgtecnologia.payment.domain.model.Customer

interface AsaasIntegrationService {
    fun createCustomerInAsaas(customer: Customer): String
    fun updateCustomerInAsaas(customer: Customer)
    fun deleteCustomerInAsaas(externalId: String)
}