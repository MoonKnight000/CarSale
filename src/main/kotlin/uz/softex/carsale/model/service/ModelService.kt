package uz.softex.carsale.model.service

import uz.softex.carsale.model.dto.ModelDto
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

interface ModelService {

    fun getCarModel(): ApiResponseGeneric<*>
    fun getCarModelById(id: Int): ApiResponseGeneric<*>
    fun getCompanyCarModels(): ApiResponseGeneric<*>
    fun addModel(modelDto: ModelDto): ApiResponse
    fun updateModel(modelDto: ModelDto): ApiResponse
    fun deleteModel(id:Int): ApiResponse
}