package tech.wgtecnologia.payment.application.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.wgtecnologia.payment.application.dto.AuthResponse
import tech.wgtecnologia.payment.application.dto.RefreshTokenRequest
import tech.wgtecnologia.payment.application.dto.RegisterRequest
import tech.wgtecnologia.payment.application.exception.UserByUsernameNotFoundException
import tech.wgtecnologia.payment.infrastructure.security.DatabaseUserDetailsService
import tech.wgtecnologia.payment.infrastructure.security.JwtService

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userService: UserService,
    private val customUserDetailsService: DatabaseUserDetailsService
) {

    fun authenticate(username: String, password: String): AuthResponse {
        val authenticationToken = UsernamePasswordAuthenticationToken(
            username,
            password
        )

        val user = userService.findByUsername(username) ?: throw UserByUsernameNotFoundException("User not found")
        val userDetails = customUserDetailsService.loadUserByUsername(user.username)
        val accessToken = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return AuthResponse(
            accessToken,
            refreshToken,
            expiresIn = 86400000 // 24 hours
        )
    }

    fun register(request: RegisterRequest): AuthResponse {
        val user = userService.createUser(request)
        val userDetails = customUserDetailsService.loadUserByUsername(user.username)

        val accessToken = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = 86400000
        )
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
        // TODO: Create a specific exception for invalid refresh tokens
        throw IllegalArgumentException("Invalid refresh token")
    }

    fun getCurrentUser(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.name
    }

}