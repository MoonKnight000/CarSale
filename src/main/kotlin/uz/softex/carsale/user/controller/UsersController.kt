package uz.softex.carsale.user.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.Users
import uz.softex.carsale.user.dto.UsersDto
import uz.softex.carsale.user.service.UsersService

@RestController
@SecurityRequirement(name = "javainuseapi")

class UsersController(private val service: UsersService) {
    @PreAuthorize(value = "hasAuthority('ENABLE_USERS')")
    @PostMapping("/enableUsers/{id}")
    fun enableUsers(@PathVariable id: Int, @RequestParam enable: Boolean): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.enableUsers(id, enable))
    }

    @PreAuthorize(value = "hasAuthority('GET_ALL_USERS_BY_COMPANY')")
    @GetMapping("/getAllUsersByCompany")
    fun getUsers(): ResponseEntity<ApiResponseGeneric<List<Users>>> {
        return ResponseEntity.ok(service.getAllUsersByCompany())
    }

    @PreAuthorize(value = "hasAuthority('GET_USERS_BY_ID')")
    @GetMapping("/getUSerById/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<ApiResponseGeneric<Users>> {
        return ResponseEntity.ok(service.getUserById(id))
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_USERS')")
    @PatchMapping("/updateUsers")
    fun updateUser(@RequestBody dto: UsersDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updateUser(dto))
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_POSITION')")
    @PatchMapping("/updatePosition")
    fun updatePosition(@RequestBody dto: UsersDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updatePositionOfUser(dto))
    }

    @PatchMapping("/updateMe")
    fun updateMe(@RequestBody dto: UsersDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updateMe(dto))
    }

    @DeleteMapping("/deleteMe")
    fun deleteMe(): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deleteMe())
    }

    @PreAuthorize(value = "hasAuthority('DELETE_USERS')")
    @DeleteMapping("/deleteUsers/{id}")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deleteUser(id))
    }
}