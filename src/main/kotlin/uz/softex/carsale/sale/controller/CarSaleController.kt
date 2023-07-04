package uz.softex.carsale.sale.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.servlet.http.HttpServletResponse
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
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.sale.dto.SaleCarDto
import uz.softex.carsale.sale.service.CarSaleService

@RestController
@RequestMapping("/contract")
@SecurityRequirement(name = "javainuseapi")
class CarSaleController(private val service: CarSaleService) {
    @PreAuthorize(value = "hasAuthority('GET_CONTRACTS')")
    @GetMapping("/getAllContracts")
    fun getAll(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getAllSales())
    }

    @PreAuthorize(value = "hasAuthority('GET_CONTRACTS')")
    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getSalesById(id))
    }

    @PreAuthorize(value = "hasAuthority('BUY')")
    @PostMapping("/saleCar")
    fun sale(@RequestBody dto: SaleCarDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.saleCar(dto))
    }

    @GetMapping("/contractToPDF/{id}")
    fun pdf(@PathVariable id: Int, response: HttpServletResponse): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.contractToPdf(id, response))
    }

    @PreAuthorize(value = "hasAuthority('DELETE_CONTRACT')")
    @DeleteMapping("/deleteContract/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.delete(id))
    }

    @PreAuthorize(value = "hasAuthority('BUY')")
    @PatchMapping("/update")
    fun update(@RequestBody dto: SaleCarDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.update(dto))
    }
    @GetMapping("/getMYContracts")
    fun getMyContracts(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getMyContracts())
    }
    @PostMapping("/viewContract")
    fun viewContract(@RequestBody @Valid dto: SaleCarDto,response: HttpServletResponse): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.viewContract(dto,response))
    }
}