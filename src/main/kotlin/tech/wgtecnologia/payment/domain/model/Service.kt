package tech.wgtecnologia.payment.domain.model

import tech.wgtecnologia.payment.domain.enums.ServiceRecurrence
import tech.wgtecnologia.payment.domain.enums.ServiceStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class Service(
    val id: String? = null,
    val name: String,
    val description: String? = null,
    val value: BigDecimal,
    val recurrence: ServiceRecurrence,
    val dueDate: LocalDate,
    val status: ServiceStatus = ServiceStatus.ACTIVE,
    val customer: Customer,
    val externalId: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(value > BigDecimal.ZERO) { "Value must be greater than zero" }
        require(dueDate.isAfter(LocalDate.now())) { "Due date must be in the future" }
    }

    fun activate(): Service = copy(status = ServiceStatus.ACTIVE)

    fun cancel(): Service = copy(status = ServiceStatus.CANCELLED)

    fun updateValue(newValue: BigDecimal): Service = copy(value = newValue)

    fun updateDueDate(newDueDate: LocalDate): Service = copy(dueDate = newDueDate)
}