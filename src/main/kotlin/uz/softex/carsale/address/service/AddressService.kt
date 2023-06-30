package uz.softex.carsale.address.service

import uz.softex.carsale.address.dto.AddressDto
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

interface AddressService {
    fun getAll(): ApiResponseGeneric<*>
    fun getById(id: Int): ApiResponseGeneric<*>
    fun getMyCompanyAddresses(): ApiResponseGeneric<*>
    fun addAddress(dto: AddressDto): ApiResponse
    fun updateAddress(dto: AddressDto): ApiResponse
    fun deleteAddress(id: Int): ApiResponse
}