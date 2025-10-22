package tech.wgtecnologia.payment.domain.service.impl

import tech.wgtecnologia.payment.domain.model.Customer
import tech.wgtecnologia.payment.domain.repository.CustomerRepository
import tech.wgtecnologia.payment.domain.service.CustomerService

class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
): CustomerService {
    override fun createCustomer(customer: Customer): Customer {
        // Check if customer with same CPF/CNPJ already exists
        customerRepository.findByCpfCnpj(customer.cpfCnpj)?.let {
            throw IllegalArgumentException("Customer with CPF/CNPJ ${customer.cpfCnpj} already exists")
        }
        return customerRepository.save(customer)
    }

    override fun getCustomerById(id: String): Customer =
        customerRepository.findById(id) ?:  throw IllegalArgumentException("Customer not found with id: $id")


    override fun getAllCustomers(): List<Customer> {
        return customerRepository.findAll()
    }

    override fun updateCustomer(customer: Customer): Customer {
        // Verify if customer exists
        getCustomerById(customer.id!!)
        return customerRepository.update(customer)
    }

    override fun deleteCustomer(id: String) {
        customerRepository.delete(id)
    }

    override fun getCustomerByCpfCnpj(cpfCnpj: String): Customer? {
        return customerRepository.findByCpfCnpj(cpfCnpj)
    }
}