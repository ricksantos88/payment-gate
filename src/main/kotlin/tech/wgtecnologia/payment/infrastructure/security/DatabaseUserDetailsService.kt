package tech.wgtecnologia.payment.infrastructure.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tech.wgtecnologia.payment.infrastructure.persistence.repository.jpa.UserJpaRepository

@Service
class DatabaseUserDetailsService(
    private val userJpaRepository: UserJpaRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userJpaRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        val authorities = userEntity.roles.flatMap { role ->
            role.permissions.map { permission ->
                SimpleGrantedAuthority(permission.name)
            } + SimpleGrantedAuthority(role.name)
        }.toSet()

        return User(
            userEntity.username,
            userEntity.password,
            userEntity.enabled,
            true,
            true,
            true,
            authorities
        )
    }
}