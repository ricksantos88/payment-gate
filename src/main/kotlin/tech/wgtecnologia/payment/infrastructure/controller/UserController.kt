package tech.wgtecnologia.payment.infrastructure.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.wgtecnologia.payment.application.dto.UserResponse
import tech.wgtecnologia.payment.domain.usecase.GetAllUsersUseCase

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management endpoints")
@SecurityRequirement(name = "bearerAuth")
class UserController(
    private val getAllUsersUseCase: GetAllUsersUseCase
) {

    @GetMapping
    @Operation(summary = "Get all users", description = "Requires USER_READ or ADMIN_READ permission")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = getAllUsersUseCase.execute().map { user ->
            UserResponse(
                id = user.id!!,
                username = user.username,
                email = user.email,
                roles = user.roles.map { it.name }.toSet()
            )
        }
        return ResponseEntity.ok(users)
    }
}
