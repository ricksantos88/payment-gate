package tech.wgtecnologia.payment.infrastructure.config

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.crypto.password.PasswordEncoder
import tech.wgtecnologia.payment.domain.repository.UserRepository
import tech.wgtecnologia.payment.domain.usecase.CreateAdminUserInput
import tech.wgtecnologia.payment.domain.usecase.CreateAdminUserUseCase

@Configuration
class InitialDataConfig {

    private val logger = LoggerFactory.getLogger(InitialDataConfig::class.java)

    @Bean
    fun createInitialAdmin(
        userRepository: UserRepository,
        createAdminUserUseCase: CreateAdminUserUseCase,
        passwordEncoder: PasswordEncoder,
        env: Environment
    ): CommandLineRunner {
        return CommandLineRunner {
            try {
                val adminUsername = env.getProperty("app.admin.username")!!
                val adminEmail = env.getProperty("app.admin.email")!!
                val adminPassword = env.getProperty("app.admin.password")!!

                val existingAdmin = userRepository.findByUsername(adminUsername)
                if (existingAdmin == null) {
                    val input = CreateAdminUserInput(
                        username = adminUsername,
                        email = adminEmail,
                        encodedPassword = passwordEncoder.encode(adminPassword)
                    )
                    createAdminUserUseCase.execute(input)
                    logger.info("Initial admin user created: $adminUsername")
                } else {
                    logger.info("Admin user already exists: $adminUsername")
                }
            } catch (e: Exception) {
                logger.error("Failed to create initial admin user", e)
            }
        }
    }
}