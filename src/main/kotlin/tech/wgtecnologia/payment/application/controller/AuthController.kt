package tech.wgtecnologia.payment.application.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech.wgtecnologia.payment.application.dto.AuthRequest
import tech.wgtecnologia.payment.application.dto.AuthResponse
import tech.wgtecnologia.payment.application.dto.RefreshTokenRequest
import tech.wgtecnologia.payment.application.dto.RegisterRequest
import tech.wgtecnologia.payment.application.service.impl.AuthService

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    @Operation(summary = "Authenticate user")
    fun login(@Valid @RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val response = authService.authenticate(request.username, request.password)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = authService.register(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    fun refreshToken(@Valid @RequestBody request: RefreshTokenRequest): ResponseEntity<AuthResponse> {
        val response = authService.refreshToken(request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info")
    fun getCurrentUser(): ResponseEntity<String> {
        val username = authService.getCurrentUser()
        return ResponseEntity.ok("Current user: $username")
    }
}