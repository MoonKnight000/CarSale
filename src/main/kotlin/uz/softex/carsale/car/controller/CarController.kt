package uz.softex.carsale.car.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import uz.softex.carsale.car.dto.CarDto
import uz.softex.carsale.car.service.CarService
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

@RestController
@RequestMapping("/cars")
@SecurityRequirement(name = "javainuseapi")
class CarController(private val service: CarService) {
    @GetMapping("/getAll")
    fun getAll(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getAllCars())
    }

    @GetMapping("/getById/{id}")
    fun getById(@RequestParam id: Int): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getById(id))
    }

    @PreAuthorize(value = "hasAuthority('ADD_CAR')")
    @PostMapping("/addCar")
    fun addCar(@RequestBody dto: CarDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.addCar(dto))
    }

    @PreAuthorize(value = "hasAuthority('ADD_CAR')")
    @PatchMapping("/updateCar")
    fun updateUsers(@RequestBody dto: CarDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updateCar(dto))
    }

    @PreAuthorize(value = "hasAuthority('DELETE_CAR')")
    @DeleteMapping("/delete/{id}")
    fun deleteCar(@RequestParam id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deleteCar(id))
    }
    @PreAuthorize(value = "hasAuthority('ADD_CAR_PHOTO')")
    @PostMapping(
        "/addCarPhotos/{id}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addPhotos(@PathVariable id: Int, @RequestParam files: MutableList<MultipartFile>): ResponseEntity<Unit> {
        return ResponseEntity.ok(service.addCarPhoto(id, files))
    }

    @PreAuthorize(value = "hasAuthority('ADD_CAR_PHOTO')")
    @PatchMapping(
        "/updateCarPhotos/{id}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updatePhotos(@PathVariable id: Int, @RequestParam files: MutableList<MultipartFile>): ResponseEntity<Unit> {
        return ResponseEntity.ok(service.updateCarPhoto(id, files))
    }

    @GetMapping("/getCarPhoto/{id}")
    fun getCarPhoto(@PathVariable id: Int, response: HttpServletResponse): ResponseEntity<Unit> {
        return ResponseEntity.ok(service.getCarPhoto(id, response))
    }
    @PreAuthorize(value = "hasAuthority('ADD_CAR_PHOTO')")
    @GetMapping("/getMyCompanyCars")
    fun getMYCompanyCars(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getMyCompanyCars())
    }

//    @MessageMapping("/message")
//    @SendTo("topic/get")
}

