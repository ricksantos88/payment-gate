package tech.wgtecnologia.payment.infrastructure.persistence.entity

import jakarta.persistence.*
import tech.wgtecnologia.payment.shared.enums.Permission
import java.util.UUID

@Entity
@Table(name = "roles")
data class RoleEntity(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val name: String,

    @Column
    val description: String? = null,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = [JoinColumn(name = "role_id")])
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    val permissions: Set<Permission> = emptySet()
)