package uz.softex.carsale.car.service

import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import uz.softex.carsale.car.dto.CarDto
import uz.softex.carsale.car.entity.Cars
import uz.softex.carsale.car.exception.CarNotFound
import uz.softex.carsale.car.repository.CarRepository
import uz.softex.carsale.image.entity.SaveImage
import uz.softex.carsale.image.repository.SaveImageRepository
import uz.softex.carsale.model.exception.CarModelNotFound
import uz.softex.carsale.model.repository.CarModelRepository
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.service.AuthService
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths

@Service
class CarServiceImp(
    private val repository: CarRepository,
    private val carModelRepository: CarModelRepository,
    private val imageRepository: SaveImageRepository,
    private val authService: AuthService
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
        val cars = Cars()
        cars.model = carModelRepository.findByIdAndCompanyId(dto.carModel!!,authService.getCurrentUser().workCompany!!.id!!).orElseThrow { throw CarModelNotFound() }
        cars.count = dto.count
        cars.price = dto.price
        cars.color = dto.color
        cars.dateOfProduced = dto.dateOfProduced
        cars.used = dto.used
        if(authService.getCurrentUser().workCompany!!.id!=cars.model!!.company!!.id)
            throw CarModelNotFound()
        repository.save(cars)
        return ApiResponse()
    }

    override fun updateCar(dto: CarDto): ApiResponse {
        val orElseThrow = repository.findByIdAndModelCompanyId(dto.id!!,authService.getCurrentUser().workCompany!!.id!!).orElseThrow { throw CarNotFound() }
        val model = carModelRepository.findByIdAndCompanyId(dto.carModel!!,authService.getCurrentUser().workCompany!!.id!!).orElseThrow { throw CarModelNotFound() }
        orElseThrow.color = dto.color
        orElseThrow.model = model
        orElseThrow.price = dto.price
        orElseThrow.dateOfProduced = dto.dateOfProduced
        orElseThrow.used = dto.used
        repository.save(orElseThrow)
        return ApiResponse()
    }

    override fun deleteCar(id: Int): ApiResponse {
        if (!repository.existsByIdAndModelCompanyId(id,authService.getCurrentUser().workCompany!!.id!!)) throw CarNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }

    @Transactional
    override fun addCarPhoto(id: Int, files: MutableList<MultipartFile>) {
        val find = repository.findByIdAndModelCompanyId(id,authService.getCurrentUser().workCompany!!.id!!).orElseThrow { throw CarNotFound() }
        var count = find.images.size + 1
        files.forEach {
            val image = SaveImage()
            image.type = it.contentType
            image.name = "${find.id}($count).${it.contentType!!.split("/")[1]}"
            image.path = "F:\\CarSaleImages\\CarImages/${image.name}"
            imageRepository.save(image)
            Files.copy(it.inputStream, Paths.get(image.path))
            find.images.add(image)
            count++
        }
        repository.save(find)
    }

    override fun updateCarPhoto(id: Int, files: MutableList<MultipartFile>) {
        val find = repository.findByIdAndModelCompanyId(id,authService.getCurrentUser().workCompany!!.id!!).orElseThrow { throw CarNotFound() }
        find.images = mutableListOf()
        var count = 0
        files.forEach {
            val image = SaveImage()
            image.type = it.contentType
            image.name = "${find.id}($count).${it.contentType!!.split("/")[1]}"
            image.path = "F:\\CarSaleImages\\CarImages/${image.name}"
            imageRepository.save(image)
            Files.copy(it.inputStream, Paths.get(image.path))
            find.images.add(image)
            count++
        }
        repository.save(find)
    }

    override fun getCarPhoto(id: Int, response: HttpServletResponse) {
        val find = repository.findById(id).orElseThrow { throw CarNotFound() }
        find.images.forEach {
            response.setHeader("Content-Disposition", "attachment; filename=\"${it!!.name}\"");
            response.contentType = it.type
            FileCopyUtils.copy(FileInputStream("F:\\CarSaleImages\\CarImages/${it.name}"), response.outputStream)
        }
    }

    override fun getMyCompanyCars(): ApiResponseGeneric<*> {
        val companyCars = repository.findByModelCompanyId(authService.getCurrentUser().workCompany!!.id!!)
        val dtoList = mutableListOf<CarDto>()
        companyCars.forEach { dtoList.add(CarDto(it)) }
        return ApiResponseGeneric(dtoList)
    }
}