package uz.softex.carsale.user.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.dto.SignIn
import uz.softex.carsale.user.dto.UsersDto
import uz.softex.carsale.user.service.AuthService

@RestController
@RequestMapping("/auth")
class AuthController(private val service: AuthService) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody dto: UsersDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.signUp(dto))
    }
    @PostMapping("/signIp")
    fun signIp(@RequestBody dto: SignIn): ResponseEntity<ApiResponseGeneric<String>> {
        return ResponseEntity.ok(service.signIn(dto))
    }
}