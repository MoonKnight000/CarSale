package uz.softex.carsale.handler

import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import uz.softex.carsale.company.exception.CompanyNotFound
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.position.exception.PositionNotFound
import uz.softex.carsale.user.exception.UserExists
import uz.softex.carsale.user.exception.UserNotEnabled
import uz.softex.carsale.user.exception.UserNotFound

@ControllerAdvice
class Handler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(UserNotFound::class)
    fun handleUserNotFound(e: UserNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("User Not Found", false))
    }

    @ExceptionHandler(PositionNotFound::class)
    fun handlePositionNotFound(e: PositionNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("Position Not Found", false))
    }

    @ExceptionHandler(UserExists::class)
    fun handleUserExists(e: UserExists): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("User exists", false))
    }

    @ExceptionHandler(UserNotEnabled::class)
    fun handleUserNotEnabled(e: UserNotEnabled): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("User Not Enable", false))
    }
    @ExceptionHandler(CompanyNotFound::class)
    fun handleCompanyNotFound(e: CompanyNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("User Not Enable", false))
    }
//    @ExceptionHandler(NotFound::class)
//    fun handleNotFound(e: NotFound): ResponseEntity<ApiResponse> {
//        return ResponseEntity.status(404).body(ApiResponse("User Not Enable", false))
//    }
}