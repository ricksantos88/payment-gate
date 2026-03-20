package tech.wgtecnologia.payment.application.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech.wgtecnologia.payment.application.dto.RegisterRequest
import tech.wgtecnologia.payment.application.service.impl.UserService

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Administrative endpoints")
@SecurityRequirement(name = "bearerAuth")
class AdminController(
    private val userService: UserService
) {

    @PostMapping("/users")
    @Operation(summary = "Create new admin user", description = "Requires ADMIN_WRITE permission")
    fun createAdminUser(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        userService.createAdminUser(request)
        return ResponseEntity.ok("Admin user created successfully")
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Admin dashboard", description = "Requires ADMIN_READ permission")
    fun adminDashboard(): ResponseEntity<String> {
        return ResponseEntity.ok("Admin dashboard - only accessible by admins")
    }
}