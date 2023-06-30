package uz.softex.carsale.position.controller

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
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.position.dto.PositionDto
import uz.softex.carsale.position.service.PositionService

@RestController
@RequestMapping("/position")
@SecurityRequirement(name = "javainuseapi")
class PositionController(private val service: PositionService) {
    @PreAuthorize(value = "hasAuthority('GET_POSITION')")
    @GetMapping("/getAll")
    fun getAll(): ResponseEntity<ApiResponseGeneric<List<PositionDto>>> {
        return ResponseEntity.ok(service.getPositions())
    }

    @PreAuthorize(value = "hasAuthority('GET_POSITION')")
    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<ApiResponseGeneric<PositionDto>> {
        return ResponseEntity.ok(service.getPositionById(id))
    }

    @GetMapping("/getMyPosition")
    fun getMyPosition(): ResponseEntity<ApiResponseGeneric<PositionDto>> {
        return ResponseEntity.ok(service.getMyPosition())
    }

    @PreAuthorize(value = "hasAuthority('GET_POSITION')")
    @PostMapping("/addPosition")
    fun addPosition(@RequestBody positionDto: PositionDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.addPosition(positionDto))
    }

    @PreAuthorize(value = "hasAuthority('ADD_POSITION')")
    @PatchMapping
    fun updatePosition(@RequestBody positionDto: PositionDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updatePosition(positionDto))
    }

    @DeleteMapping("/deletePosition/{id}")
    fun deletePosition(@PathVariable id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deletePosition(id))
    }
}