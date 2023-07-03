package uz.softex.carsale.model.service

import org.springframework.stereotype.Service
import uz.softex.carsale.company.exception.CompanyNotFound
import uz.softex.carsale.company.repository.CompanyRepository
import uz.softex.carsale.model.dto.ModelDto
import uz.softex.carsale.model.entity.CarModel
import uz.softex.carsale.model.repository.CarModelRepository
import uz.softex.carsale.model.exception.CarModelNotFound
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.service.AuthService

@Service
class ModelServiceImp(
    private val repository: CarModelRepository,
    private val authService: AuthService,
    private val companyRepository: CompanyRepository
) : ModelService {
    override fun getCarModel(): ApiResponseGeneric<*> {
        val findAll = repository.findAll()
        val dtoList = mutableListOf<ModelDto>()
        findAll.forEach {
            dtoList.add(ModelDto(it))
        }
        return ApiResponseGeneric(dtoList)
    }

    override fun getCarModelById(id: Int): ApiResponseGeneric<*> {
        return ApiResponseGeneric(ModelDto(repository.findById(id).orElseThrow { CarModelNotFound() }))
    }

    override fun getCompanyCarModels(): ApiResponseGeneric<*> {
        if (authService.getCurrentUser().workCompany == null) throw CompanyNotFound()
        val findAll = repository.findByCompanyId(authService.getCurrentUser().workCompany!!.id!!)
        val dtoList = mutableListOf<ModelDto>()
        findAll.forEach {
            dtoList.add(ModelDto(it))
        }
        return ApiResponseGeneric(dtoList)
    }


    override fun addModel(modelDto: ModelDto): ApiResponse {
        if (modelDto.company!! != authService.getCurrentUser().workCompany!!.id!!) throw CompanyNotFound()
        repository.save(
            CarModel(
                modelDto,
                authService.getCurrentUser().workCompany
            )
        )
        return ApiResponse()
    }

    override fun updateModel(modelDto: ModelDto): ApiResponse {
        val findById = repository.findByIdAndCompanyId(modelDto.id!!, authService.getCurrentUser().workCompany!!.id!!)
            .orElseThrow { throw CarModelNotFound() }
        findById.carSunroof = modelDto.carSunroof
        findById.name = modelDto.name
        findById.engine = modelDto.engine
        findById.company = companyRepository.findById(modelDto.id!!).orElseThrow { throw CompanyNotFound() }
        findById.fuelType = modelDto.fuelType
        findById.seats = modelDto.seats
        findById.transmission = modelDto.transmission
        findById.speed = modelDto.speed
        repository.save(findById)
        return ApiResponse()
    }

    override fun deleteModel(id: Int): ApiResponse {
        if (!repository.existsByIdAndCompanyId(
                id,
                authService.getCurrentUser().workCompany!!.id!!
            )
        ) throw CarModelNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }
}