package uz.softex.carsale.company.service

import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import uz.softex.carsale.address.exception.AddressNotFound
import uz.softex.carsale.address.repository.AddressRepository
import uz.softex.carsale.company.dto.CompanyDto
import uz.softex.carsale.company.entity.Company
import uz.softex.carsale.company.exception.CompanyNotFound
import uz.softex.carsale.company.repository.CompanyRepository
import uz.softex.carsale.image.entity.SaveImage
import uz.softex.carsale.image.repository.SaveImageRepository
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.service.AuthService
import java.io.File
import java.io.FileInputStream

@Service
class CompanyServiceImp(
    private val repository: CompanyRepository,
    private val addressRepository: AddressRepository,
    private val authService: AuthService,
    private val imageRepository: SaveImageRepository
) : CompanyService {
    override fun getAllCompany(): ApiResponseGeneric<*> {
        val findAll = repository.findAll()
        val dtoList = mutableListOf<CompanyDto>()
        findAll.forEach { dtoList.add(CompanyDto(it)) }
        return ApiResponseGeneric(dtoList)
    }

    override fun getById(id: Int): ApiResponseGeneric<*> {
        return ApiResponseGeneric(CompanyDto(repository.findById(id).orElseThrow { throw CompanyNotFound() }))
    }

    override fun getMyCompany(): ApiResponseGeneric<*> {
        return ApiResponseGeneric(CompanyDto(authService.getCurrentUser().workCompany ?: throw CompanyNotFound()))
    }

    override fun addCompany(dto: CompanyDto, file: MultipartFile): ApiResponse {
        val image = SaveImage()
        image.type = file.contentType
        image.name = "${dto.name}.${file.contentType!!.split("/")[1]}"
        image.path = "F:\\CarSaleImages\\CompanyLogos/${image.name}"
        imageRepository.save(image)
        val company = Company()
        company.name = dto.name
        company.address = mutableListOf()
        company.image = image
        dto.address!!.forEach {
            company.address!!.add(addressRepository.findById(it).orElseThrow { throw AddressNotFound() })
        }
        company.phoneNumber = dto.phoneNumber
        repository.save(company)
        return ApiResponse()
    }

    override fun updateCompany(dto: CompanyDto, file: MultipartFile): ApiResponse {
        val company = repository.findById(dto.id!!).orElseThrow { throw CompanyNotFound() }
        val deleteFile = File("F:\\CarSaleImages\\CompanyLogos/${company.image!!.name}")
        deleteFile.delete()
        val image = SaveImage()
        image.type = file.contentType
        image.name = "${dto.name}.${file.contentType!!.split("/")[1]}"
        image.path = "F:\\CarSaleImages\\CompanyLogos/${image.name}"
        imageRepository.save(image)
        company.name = dto.name
        dto.address!!.forEach {
            company.address!!.add(addressRepository.findById(it).orElseThrow { throw AddressNotFound() })
        }
        company.phoneNumber = dto.phoneNumber
        repository.save(company)
        return ApiResponse()
    }

    override fun deleteCompany(id: Int): ApiResponse {
        if (!repository.existsById(id)) throw CompanyNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }

    override fun getLogo(response: HttpServletResponse, companyId: Int) {
        val findById = repository.findById(companyId).orElseThrow { throw CompanyNotFound() }
        val image = findById.image
        response.setHeader("Content-Disposition", "attachment; filename=\"${image!!.name}\"");
        response.contentType = image.type
        FileCopyUtils.copy(FileInputStream("F:\\CarSaleImages\\CompanyLogos/${image.name}"), response.outputStream)
    }
}