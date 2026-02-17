package tech.wgtecnologia.payment.application.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

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
    ])
    fun handleException(ex: Exception): ResponseEntity<Any> {
        return ResponseEntity.ok(
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