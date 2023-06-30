package uz.softex.carsale.company.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import uz.softex.carsale.company.dto.CompanyDto
import uz.softex.carsale.company.service.CompanyService
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

@RestController
@RequestMapping("/company")
@SecurityRequirement(name = "javainuseapi")
class CompanyController(private val service: CompanyService) {
    @GetMapping("/getAllCompany")
    fun getAll(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getAllCompany())
    }

    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getById(id))
    }

    @GetMapping("/getMyCompany")
    fun getMyCompany(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getMyCompany())
    }

    @PatchMapping("/update",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateCompany(@RequestBody dto: CompanyDto, @RequestParam file: MultipartFile): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updateCompany(dto, file))
    }

    @PostMapping(
        "/addCompany",
//        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
//        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addCompany(@RequestBody dto: CompanyDto, @RequestParam file: MultipartFile): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.addCompany(dto, file))
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deleteCompany(id))
    }
}