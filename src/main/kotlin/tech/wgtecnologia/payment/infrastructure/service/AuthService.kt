package tech.wgtecnologia.payment.infrastructure.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.wgtecnologia.payment.application.dto.AuthResponse
import tech.wgtecnologia.payment.application.dto.RefreshTokenRequest
import tech.wgtecnologia.payment.application.exception.UserByUsernameNotFoundException
import tech.wgtecnologia.payment.domain.repository.UserRepository
import tech.wgtecnologia.payment.domain.usecase.CreateAdminUserInput
import tech.wgtecnologia.payment.domain.usecase.CreateAdminUserUseCase
import tech.wgtecnologia.payment.domain.usecase.RegisterUserInput
import tech.wgtecnologia.payment.domain.usecase.RegisterUserUseCase
import tech.wgtecnologia.payment.infrastructure.security.DatabaseUserDetailsService
import tech.wgtecnologia.payment.infrastructure.security.JwtService

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val customUserDetailsService: DatabaseUserDetailsService,
    private val registerUserUseCase: RegisterUserUseCase,
    private val createAdminUserUseCase: CreateAdminUserUseCase,
    private val passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder
) {

    fun authenticate(username: String, password: String): AuthResponse {
        userRepository.findByUsername(username)
            ?: throw UserByUsernameNotFoundException("User not found")

        val userDetails = customUserDetailsService.loadUserByUsername(username)
        val accessToken = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = 86400000
        )
    }

    fun register(username: String, email: String, password: String): AuthResponse {
        val input = RegisterUserInput(
            username = username,
            email = email,
            encodedPassword = passwordEncoder.encode(password)
        )
        val user = registerUserUseCase.execute(input)
        val userDetails = customUserDetailsService.loadUserByUsername(user.username)

        val accessToken = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = 86400000
        )
    }

    fun registerAdmin(username: String, email: String, password: String) {
        val input = CreateAdminUserInput(
            username = username,
            email = email,
            encodedPassword = passwordEncoder.encode(password)
        )
        createAdminUserUseCase.execute(input)
    }

    fun refreshToken(request: RefreshTokenRequest): AuthResponse {
        val refreshToken = request.refreshToken
        val username = jwtService.extractUsername(refreshToken)

        if (username.isNotBlank()) {
            val userDetails = customUserDetailsService.loadUserByUsername(username)
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                val accessToken = jwtService.generateToken(userDetails)
                val newRefreshToken = jwtService.generateRefreshToken(userDetails)

                return AuthResponse(
                    accessToken = accessToken,
                    refreshToken = newRefreshToken,
                    expiresIn = 86400000
                )
            }
        }
        throw IllegalArgumentException("Invalid refresh token")
    }

    fun getCurrentUser(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.name
    }
}
