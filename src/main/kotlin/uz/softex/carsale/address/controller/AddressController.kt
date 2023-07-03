package uz.softex.carsale.address.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.softex.carsale.address.dto.AddressDto
import uz.softex.carsale.address.service.AddressService
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

@RestController
@RequestMapping("/address")
@SecurityRequirement(name = "javainuseapi")
class AddressController(private val service: AddressService) {
    @GetMapping("/getAll")
    fun getAll(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getAll())
    }

    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getById(id))
    }

    @GetMapping("/getMyCompanyAddresses")
    fun getMyCompanyAddresses(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getMyCompanyAddresses())
    }

    @PreAuthorize(value = "hasAuthority('ADD_ADDRESS')")
    @PostMapping("/addAddress")
    fun addAddress(@RequestBody dto: AddressDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.addAddress(dto))
    }

    @PreAuthorize(value = "hasAuthority('ADD_ADDRESS')")
    @PatchMapping("/updateAddress")
    fun updateAddress(@RequestBody dto: AddressDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updateAddress(dto))
    }

    @PreAuthorize(value = "hasAuthority('ADD_ADDRESS')")
    @DeleteMapping("/deleteById/{id}")
    fun deleteAddress(@PathVariable id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deleteAddress(id))
    }
}