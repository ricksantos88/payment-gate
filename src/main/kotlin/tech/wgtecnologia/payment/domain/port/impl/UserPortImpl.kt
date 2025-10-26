package tech.wgtecnologia.payment.domain.port.impl

import org.springframework.stereotype.Service
import tech.wgtecnologia.payment.domain.exception.UserByEmailNotFoundException
import tech.wgtecnologia.payment.domain.exception.UserByIdNotFoundException
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.port.UserPort
import tech.wgtecnologia.payment.infrastructure.persistence.repository.UserRepository
import java.util.UUID

@Service
class UserPortImpl(
    private val userRepository: UserRepository
): UserPort {
    override fun findByUsername(username: String): User? =
        userRepository.findByUsername(username)

    // TODO: implements custom exception handling
    override fun findByEmail(email: String): User =
        userRepository.findByEmail(email) ?: throw UserByEmailNotFoundException("user under -$email- not found")

    override fun findById(id: UUID): User =
        userRepository.findById(id) ?: throw UserByIdNotFoundException("user under $id not found")

    override fun save(user: User): User =
        userRepository.save(user)

    override fun existsByUsername(username: String): Boolean =
        userRepository.existsByUsername(username)

    override fun existsByEmail(email: String): Boolean =
        userRepository.existsByEmail(email)

    override fun findAll(): List<User> = emptyList()
}