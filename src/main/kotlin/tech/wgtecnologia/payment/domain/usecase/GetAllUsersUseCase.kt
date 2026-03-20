package tech.wgtecnologia.payment.domain.usecase

import tech.wgtecnologia.payment.domain.model.User
import tech.wgtecnologia.payment.domain.repository.UserRepository

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {

    fun execute(): List<User> {
        return userRepository.findAll()
    }
}
