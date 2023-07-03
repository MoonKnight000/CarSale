package uz.softex.carsale.company.service

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.multipart.MultipartFile
import uz.softex.carsale.company.dto.CompanyDto
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric

interface CompanyService {
    fun getAllCompany(): ApiResponseGeneric<*>
    fun getById(id: Int): ApiResponseGeneric<*>
    fun getMyCompany(): ApiResponseGeneric<*>
    fun addCompany(dto: CompanyDto): ApiResponse
    fun updateCompany(dto: CompanyDto): ApiResponse
    fun deleteCompany(id: Int): ApiResponse
    fun getLogo(response: HttpServletResponse, companyId: Int)
    fun addLogo(id: Int, file: MultipartFile):ApiResponse
    fun updateLogo(id: Int, file: MultipartFile):ApiResponse
}