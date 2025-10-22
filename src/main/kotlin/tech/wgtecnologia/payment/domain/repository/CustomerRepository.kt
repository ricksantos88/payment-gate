package tech.wgtecnologia.payment.domain.repository


import tech.wgtecnologia.payment.domain.model.Customer
import java.util.*

interface CustomerRepository {
    fun save(customer: Customer): Customer
    fun findById(id: String): Customer?
    fun findAll(): List<Customer>
    fun update(customer: Customer): Customer
    fun delete(id: String)
    fun findByCpfCnpj(cpfCnpj: String): Customer?
    fun findByExternalId(externalId: String): Customer?
}