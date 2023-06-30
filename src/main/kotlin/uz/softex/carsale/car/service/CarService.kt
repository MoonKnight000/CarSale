package uz.softex.carsale.car.service

import uz.softex.carsale.car.dto.CarDto
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

interface CarService {
fun getAllCars():ApiResponseGeneric<*>
fun getById(id:Int):ApiResponseGeneric<*>
fun addCar(dto: CarDto):ApiResponse
fun updateCar(dto:CarDto):ApiResponse
fun deleteCar(id:Int):ApiResponse
}