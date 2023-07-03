package uz.softex.carsale.model.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
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
import uz.softex.carsale.model.dto.ModelDto
import uz.softex.carsale.model.service.ModelService
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

@RestController
@RequestMapping
@SecurityRequirement(name = "javainuseapi")
class CarModelController(private val service: ModelService) {
    @GetMapping("/getAll")
    fun getAll(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getCarModel())
    }

    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getCarModelById(id))
    }

    @GetMapping("/getAllMyCompanyCarModels")
    fun getAllCompanyCarModels(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getCompanyCarModels())
    }

    @PreAuthorize(value = "hasAuthority('ADD_CAR_MODEL')")
    @PostMapping("/add")
    fun add(@RequestBody dto: ModelDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.addModel(dto))
    }

    @PreAuthorize(value = "hasAuthority('ADD_CAR_MODEL')")
    @PatchMapping("/update")
    fun update(@RequestBody dto: ModelDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updateModel(dto))
    }

    @PreAuthorize(value = "hasAuthority('DELETE_CAR_MODEL')")
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deleteModel(id))
    }

}