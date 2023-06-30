package uz.softex.carsale.address.service

import org.springframework.stereotype.Service
import uz.softex.carsale.address.dto.AddressDto
import uz.softex.carsale.address.entity.Address
import uz.softex.carsale.address.exception.AddressNotFound
import uz.softex.carsale.address.repository.AddressRepository
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.service.AuthService

@Service
class AddressServiceImp(
    private val repository: AddressRepository,
    private val authService: AuthService
) : AddressService {
    override fun getAll(): ApiResponseGeneric<*> {
        val findAll = repository.findAll()
        val dtoList = mutableListOf<AddressDto>()
        findAll.forEach {
            val dto = AddressDto()
            dto.city = it.city
            dto.id = it.id
            dto.houseNumber = it.houseNumber
            dto.region = it.region
            dto.street = it.street
            dtoList.add(dto)
        }
        return ApiResponseGeneric(dtoList)
    }

    override fun getById(id: Int): ApiResponseGeneric<*> {
        val it = repository.findById(id).orElseThrow { throw AddressNotFound() }
        val dto = AddressDto()
        dto.city = it.city
        dto.id = it.id
        dto.houseNumber = it.houseNumber
        dto.region = it.region
        dto.street = it.street
        return ApiResponseGeneric(dto)
    }

    override fun getMyCompanyAddresses(): ApiResponseGeneric<*> {
        if (authService.getCurrentUser().workCompany == null) throw AddressNotFound()
        val findAll = authService.getCurrentUser().workCompany!!.address
        val dtoList = mutableListOf<AddressDto>()
        findAll!!.forEach {
            val dto = AddressDto()
            dto.city = it.city
            dto.id = it.id
            dto.houseNumber = it.houseNumber
            dto.region = it.region
            dto.street = it.street
            dtoList.add(dto)
        }
        return ApiResponseGeneric(dtoList)
    }

    override fun addAddress(dto: AddressDto): ApiResponse {
        val address = Address()
        address.city = dto.city
        address.houseNumber = dto.houseNumber
        address.street = dto.street
        address.region = dto.region
        repository.save(address)
        return ApiResponse()
    }

    override fun updateAddress(dto: AddressDto): ApiResponse {
        val address = repository.findById(dto.id!!).orElseThrow { throw AddressNotFound() }
        address.city = dto.city
        address.houseNumber = dto.houseNumber
        address.street = dto.street
        address.region = dto.region
        repository.save(address)
        return ApiResponse()
    }

    override fun deleteAddress(id: Int): ApiResponse {
        if (!repository.existsById(id)) throw AddressNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }
}