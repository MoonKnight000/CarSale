package uz.softex.carsale.position.service

import org.springframework.stereotype.Service
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.position.entity.Position
import uz.softex.carsale.position.dto.PositionDto
import uz.softex.carsale.position.exception.PositionNotFound
import uz.softex.carsale.position.repository.PositionRepository
import uz.softex.carsale.user.service.AuthService
@Service
class PositionServiceImp(
    private val repository: PositionRepository,
    private val authService: AuthService
) : PositionService {
    override fun getPositions(): ApiResponseGeneric<List<PositionDto>> {
        val findAll = repository.findAll()
        val allDto = mutableListOf<PositionDto>()
        findAll.forEach {
            allDto.add(PositionDto(it.id!!, it.name!!, it.permissions!!))
        }
        return ApiResponseGeneric(allDto)
    }

    override fun getPositionById(id: Int): ApiResponseGeneric<PositionDto> {
        val it = repository.findById(id).orElseThrow { throw PositionNotFound() }
        return ApiResponseGeneric(PositionDto(it.id!!, it.name!!, it.permissions!!))
    }

    override fun getMyPosition(): ApiResponseGeneric<PositionDto> {
        val it = authService.getCurrentUser().position
        return ApiResponseGeneric(PositionDto(it!!.id!!, it.name!!, it.permissions!!))
    }

    override fun addPosition(positionDto: PositionDto): ApiResponse {
        val position = Position()
        position.name = positionDto.name
        position.permissions = positionDto.permissions
        repository.save(position)
        return ApiResponse()
    }

    override fun updatePosition(positionDto: PositionDto): ApiResponse {
        val findById = repository.findById(positionDto.id).orElseThrow { throw PositionNotFound() }
        findById.permissions = positionDto.permissions
        findById.name = positionDto.name
        repository.save(findById)
        return ApiResponse()
    }

    override fun deletePosition(id: Int): ApiResponse {
        if (!repository.existsById(id)) throw PositionNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }
}