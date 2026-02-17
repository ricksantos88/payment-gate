package tech.wgtecnologia.payment.infrastructure.persistence.repository.impl

import org.springframework.stereotype.Repository
import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.infrastructure.persistence.repository.jpa.UserJpaRepository
import tech.wgtecnologia.payment.infrastructure.persistence.mapper.UserMapper
import tech.wgtecnologia.payment.infrastructure.persistence.repository.UserRepository
import java.util.*

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
    private val userMapper: UserMapper
) : UserRepository {

    override fun findByUsername(username: String): User? {
        return userJpaRepository.findByUsername(username)?.let(userMapper::toDomain)
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.let(userMapper::toDomain)
    }

    override fun findById(id: UUID): User? {
        return userJpaRepository.findById(id).get().let(userMapper::toDomain)
    }

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val savedEntity = userJpaRepository.save(entity)
        return userMapper.toDomain(savedEntity)
    }

    override fun existsByUsername(username: String): Boolean {
        return userJpaRepository.existsByUsername(username)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun findAll(): List<User> {
        return userJpaRepository.findAll().map(userMapper::toDomain)
    }
}