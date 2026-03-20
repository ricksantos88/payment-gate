package tech.wgtecnologia.payment.infrastructure.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech.wgtecnologia.payment.application.exception.CreateUserAdminNotPermittedException
import tech.wgtecnologia.payment.application.exception.EmailAlreadyExistsException
import tech.wgtecnologia.payment.application.exception.RoleNotFoundException
import tech.wgtecnologia.payment.application.exception.UserAlreadyExistsException
import tech.wgtecnologia.payment.application.exception.UserByUsernameNotFoundException
import tech.wgtecnologia.payment.domain.exception.UserByEmailNotFoundException
import tech.wgtecnologia.payment.domain.exception.UserByIdNotFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.map { error ->
            val fieldName = error.field.replace(Regex("(\\p{Upper})"), { "_${it.value.lowercase()}" })
            val errorMessage = error.defaultMessage ?: "Validation error"
            buildMap {
                put(FIELD, fieldName)
                put(MESSAGE, errorMessage)
                put(REJECTED_VALUE, error.rejectedValue)
            }
        }
        return ResponseEntity(
            buildMap {
                put(HTTP_STATUS, HttpStatus.BAD_REQUEST.value())
                put("errors", errors)
            },
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [
        Exception::class,
        RuntimeException::class,
        IllegalArgumentException::class,
        CreateUserAdminNotPermittedException::class,
        EmailAlreadyExistsException::class,
        RoleNotFoundException::class,
        UserAlreadyExistsException::class,
        UserByUsernameNotFoundException::class,
        UserByEmailNotFoundException::class,
        UserByIdNotFoundException::class,
    ])
    fun handleException(ex: Exception): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(
            buildMap {
                put(HTTP_STATUS, HttpStatus.BAD_REQUEST.value())
                put(MESSAGE, ex.message ?: "An error occurred")
            }
        )
    }

    companion object {
        const val HTTP_STATUS = "httpStatus"
        const val MESSAGE = "message"
        const val FIELD = "field"
        const val REJECTED_VALUE = "rejected_value"
    }
}
