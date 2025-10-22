package tech.wgtecnologia.payment.domain.service

import tech.wgtecnologia.payment.domain.model.Customer
import tech.wgtecnologia.payment.domain.repository.CustomerRepository

interface CustomerService{

    fun createCustomer(customer: Customer): Customer
    fun getCustomerById(id: String): Customer?
    fun getAllCustomers(): List<Customer>
    fun updateCustomer(customer: Customer): Customer
    fun deleteCustomer(id: String)
    fun getCustomerByCpfCnpj(cpfCnpj: String): Customer?
}