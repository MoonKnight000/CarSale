package uz.softex.carsale.user.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import uz.softex.carsale.company.exception.CompanyNotFound
import uz.softex.carsale.company.repository.CompanyRepository
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.position.exception.PositionNotFound
import uz.softex.carsale.position.repository.PositionRepository
import uz.softex.carsale.user.Users
import uz.softex.carsale.user.dto.UsersDto
import uz.softex.carsale.user.exception.UserNotFound
import uz.softex.carsale.user.repository.UsersRepository

@Service
class UsersServiceImp(
    private val repository: UsersRepository,
    private val authService: AuthService,
    private val positionRepository: PositionRepository,
    private val encoder: PasswordEncoder,
    private val companyRepository: CompanyRepository
) : UsersService {


    override fun getAllUsersByCompany(): ApiResponseGeneric<*> {
        val user = authService.getCurrentUser()
        val findByWorkCompanyId = repository.findByWorkCompanyId(user.workCompany!!.id!!)
        val dtoList = mutableListOf<UsersDto>()
        findByWorkCompanyId.forEach { dtoList.add(UsersDto(it)) }
        return ApiResponseGeneric(dtoList)
    }

    override fun getUserById(id: Int): ApiResponseGeneric<*> {
        val findByIdAndWorkCompanyId = repository.findById(id).orElseThrow {
            throw UserNotFound()
        }
        return ApiResponseGeneric(UsersDto(findByIdAndWorkCompanyId))
    }

    override fun updateUser(dto: UsersDto): ApiResponse {
        val updateUser: Users? = if (authService.getCurrentUser().position!!.name == "ProjectManager")
            repository.findById(dto.id!!).orElseThrow { throw UserNotFound() }
        else
            repository.findByIdAndWorkCompanyId(dto.id!!, authService.getCurrentUser().workCompany!!.id!!)
                .orElseThrow { throw UserNotFound() }
        updateUser!!.fullName = dto.fullName
        updateUser.login = dto.login
        updateUser.parol = encoder.encode(dto.parol)
        repository.save(updateUser)
        return ApiResponse()
    }

    override fun deleteUser(id: Int): ApiResponse {
        if (authService.getCurrentUser().position!!.name == "ProjectManager") {
            if (!repository.existsById(id)) throw UserNotFound()
        } else if (!repository.existsByIdAndWorkCompanyId(
                id,
                authService.getCurrentUser().workCompany!!.id!!
            )
        ) throw UserNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }

    override fun updatePositionOfUser(dto: UsersDto): ApiResponse {
        var orElseThrow: Users? = null
        if (authService.getCurrentUser().position!!.name == "ProjectManager")
            orElseThrow = repository.findById(dto.id!!).orElseThrow { throw UserNotFound() }
        else orElseThrow =
            repository.findByIdAndWorkCompanyId(dto.id!!, authService.getCurrentUser().workCompany!!.id!!)
                .orElseThrow { throw UserNotFound() }
        orElseThrow!!.position = positionRepository.findById(dto.position!!).orElseThrow { throw PositionNotFound() }
        repository.save(orElseThrow)
        return ApiResponse()
    }

    override fun updateMe(dto: UsersDto): ApiResponse {
        val currentUser = authService.getCurrentUser()
        currentUser.login = dto.login
        currentUser.parol = encoder.encode(dto.parol)
        currentUser.fullName = dto.fullName
        repository.save(currentUser)
        return ApiResponse()
    }

    override fun deleteMe(): ApiResponse {
        repository.deleteById(authService.getCurrentUser().id!!)
        return ApiResponse()
    }

    override fun addUser(dto: UsersDto): ApiResponse {
        val users = Users()
        users.fullName = dto.fullName
        users.parol = encoder.encode(dto.parol)
        users.login = dto.login
        users.position = positionRepository.findById(dto.position!!).orElseThrow { throw PositionNotFound() }
        if (authService.getCurrentUser().position!!.name == "ProjectManager")
            users.workCompany = companyRepository.findById(dto.workCompany!!).orElseThrow { throw CompanyNotFound() }
        else
            users.workCompany = authService.getCurrentUser().workCompany
        repository.save(users)
        return ApiResponse()
    }
}