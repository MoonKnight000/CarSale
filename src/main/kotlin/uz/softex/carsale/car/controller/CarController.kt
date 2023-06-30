package uz.softex.carsale.car.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uz.softex.carsale.car.dto.CarDto
import uz.softex.carsale.car.service.CarService
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

@RestController
@RequestMapping("/cars")
class CarController(private val service: CarService) {
    @GetMapping("/getAll")
    fun getAll(): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getAllCars())
    }

    @GetMapping("/getById/{id}")
    fun getById(@RequestParam id: Int): ResponseEntity<ApiResponseGeneric<*>> {
        return ResponseEntity.ok(service.getById(id))
    }

    @PostMapping("/addCar")
    fun addCar(@RequestBody dto: CarDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.addCar(dto))
    }

    @PatchMapping("/updateCar")
    fun updateUsers(@RequestBody dto: CarDto): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.updateCar(dto))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCar(@RequestParam id: Int): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(service.deleteCar(id))
    }
}

