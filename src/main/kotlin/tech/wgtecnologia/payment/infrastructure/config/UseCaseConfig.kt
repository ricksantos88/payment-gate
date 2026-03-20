package tech.wgtecnologia.payment.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.wgtecnologia.payment.domain.repository.RoleRepository
import tech.wgtecnologia.payment.domain.repository.UserRepository
import tech.wgtecnologia.payment.domain.usecase.CreateAdminUserUseCase
import tech.wgtecnologia.payment.domain.usecase.GetAllUsersUseCase
import tech.wgtecnologia.payment.domain.usecase.RegisterUserUseCase

@Configuration
class UseCaseConfig(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    @Value("\${app.create-admin-user-permitted:true}") private val createAdminUserPermitted: Boolean
) {

    @Bean
    fun registerUserUseCase(): RegisterUserUseCase =
        RegisterUserUseCase(userRepository, roleRepository)

    @Bean
    fun createAdminUserUseCase(): CreateAdminUserUseCase =
        CreateAdminUserUseCase(userRepository, roleRepository, createAdminUserPermitted)

    @Bean
    fun getAllUsersUseCase(): GetAllUsersUseCase =
        GetAllUsersUseCase(userRepository)
}
