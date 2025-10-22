package tech.wgtecnologia.payment.domain.model

import java.time.LocalDateTime

data class Customer(
    val id: String? = null,
    val name: String,
    val email: String,
    val phone: String,
    val cpfCnpj: String,
    val postalCode: String? = null,
    val address: String? = null,
    val addressNumber: String? = null,
    val complement: String? = null,
    val province: String? = null,
    val externalId: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
    val metadata: ServiceMetadata = ServiceMetadata.empty(),
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.isNotBlank()) { "Email cannot be blank" }
        require(phone.isNotBlank()) { "Phone cannot be blank" }
        require(cpfCnpj.isNotBlank()) { "CPF/CNPJ cannot be blank" }
    }
}