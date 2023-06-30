package uz.softex.carsale.car.service

import org.springframework.stereotype.Service
import uz.softex.carsale.car.dto.CarDto
import uz.softex.carsale.car.entity.Cars
import uz.softex.carsale.car.exception.CarNotFound
import uz.softex.carsale.car.repository.CarRepository
import uz.softex.carsale.model.exception.CarModelNotFound
import uz.softex.carsale.model.repository.CarModelRepository
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

@Service
class CarServiceImp(
    private val repository: CarRepository,
    private val carModelRepository: CarModelRepository
) : CarService {
    override fun getAllCars(): ApiResponseGeneric<*> {
        val findAll = repository.findAll()
        val dtoList = mutableListOf<CarDto>()
        findAll.forEach {
            dtoList.add(CarDto(it))
        }
        return ApiResponseGeneric(dtoList)
    }

    override fun getById(id: Int): ApiResponseGeneric<*> {
        val orElseThrow = repository.findById(id).orElseThrow { throw CarNotFound() }
        return ApiResponseGeneric(CarDto(orElseThrow))
    }

    override fun addCar(dto: CarDto): ApiResponse {
        dto.id = null
        repository.save(Cars(dto, carModelRepository.findById(dto.carModel!!).orElseThrow { throw CarModelNotFound() }))
        return ApiResponse()
    }

    override fun updateCar(dto: CarDto): ApiResponse {
        val orElseThrow = repository.findById(dto.id!!).orElseThrow { throw CarNotFound() }
        val model = carModelRepository.findById(dto.carModel!!).orElseThrow { throw CarModelNotFound() }
        orElseThrow.color = dto.color
        orElseThrow.model = model
        orElseThrow.price = dto.price
        repository.save(orElseThrow)
        return ApiResponse()
    }

    override fun deleteCar(id: Int): ApiResponse {
        if (!repository.existsById(id)) throw CarNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }
}