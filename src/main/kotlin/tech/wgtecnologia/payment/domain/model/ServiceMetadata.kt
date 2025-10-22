package tech.wgtecnologia.payment.domain.model

import com.fasterxml.jackson.annotation.JsonValue

data class ServiceMetadata(
    @JsonValue
    val metadata: Map<String, Any> = emptyMap()
) {
    fun getValue(key: String): Any? = metadata[key]

    fun addMetadata(key: String, value: Any): ServiceMetadata {
        val newMetadata = metadata.toMutableMap()
        newMetadata[key] = value
        return ServiceMetadata(newMetadata)
    }

    companion object {
        fun empty(): ServiceMetadata = ServiceMetadata()
    }
}