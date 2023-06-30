package uz.softex.carsale.position.service

import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.position.dto.PositionDto

interface PositionService {
    fun getPositions(): ApiResponseGeneric<List<PositionDto>>
    fun getPositionById(id: Int): ApiResponseGeneric<PositionDto>
    fun getMyPosition(): ApiResponseGeneric<PositionDto>
    fun addPosition(positionDto: PositionDto): ApiResponse
    fun updatePosition(positionDto: PositionDto): ApiResponse
    fun deletePosition(id: Int): ApiResponse
}