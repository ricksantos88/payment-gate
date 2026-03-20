package tech.wgtecnologia.payment.infrastructure.config

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import tech.wgtecnologia.payment.application.dto.RegisterRequest
import tech.wgtecnologia.payment.application.service.impl.UserService

@Configuration
class InitialDataConfig {

    private val logger = LoggerFactory.getLogger(InitialDataConfig::class.java)

    @Bean
    fun createInitialAdmin(userService: UserService, env: Environment): CommandLineRunner {
        return CommandLineRunner {
            try {
                val adminUsername = env.getProperty("app.admin.username")!!
                val adminEmail = env.getProperty("app.admin.email")!!
                val adminPassword = env.getProperty("app.admin.password")!!

                val existingAdmin = userService.findByUsername(adminUsername)
                if (existingAdmin == null) {
                    val request = RegisterRequest(
                        username = adminUsername,
                        email = adminEmail,
                        password = adminPassword
                    )
                    userService.createAdminUser(request)
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