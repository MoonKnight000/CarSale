package uz.softex.carsale.user.service

import org.springframework.stereotype.Service
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
    private val positionRepository: PositionRepository
) : UsersService {

    override fun enableUsers(id: Int, enable: Boolean): ApiResponse {
        val user = authService.getCurrentUser()
        val findById = repository.findById(id).orElseThrow { throw UserNotFound() }
        findById.enabled = true
        if (user.position!!.name != "ProjectManager") {
            findById.workCompany = user.workCompany
        }
        repository.save(findById)
        return ApiResponse()
    }

    override fun getAllUsersByCompany(): ApiResponseGeneric<List<Users>> {
        val user = authService.getCurrentUser()

        return ApiResponseGeneric(repository.findByWorkCompanyId(user.workCompany!!.id!!))
    }

    override fun getUserById(id: Int): ApiResponseGeneric<Users> {
        val findByIdAndWorkCompanyId = repository.findById(id).orElseThrow {
            throw UserNotFound()
        }
        return ApiResponseGeneric(findByIdAndWorkCompanyId)
    }

    override fun updateUser(dto: UsersDto): ApiResponse {
        val user = authService.getCurrentUser()
        val updateUser =
            repository.findByIdAndWorkCompanyId(dto.id!!, user.workCompany!!.id!!).orElseThrow { throw UserNotFound() }
        updateUser.fullName = dto.fullName
        updateUser.login = dto.login
        updateUser.parol = dto.parol
        repository.save(updateUser)
        return ApiResponse()
    }

    override fun deleteUser(id: Int): ApiResponse {
        if (!repository.existsById(id)) throw UserNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }

    override fun updatePositionOfUser(dto: UsersDto): ApiResponse {
        val orElseThrow = repository.findById(dto.id!!).orElseThrow { throw UserNotFound() }
        orElseThrow.position = positionRepository.findById(dto.position!!).orElseThrow { throw PositionNotFound() }
        repository.save(orElseThrow)
        return ApiResponse()
    }

    override fun updateMe(dto: UsersDto): ApiResponse {
        val currentUser = authService.getCurrentUser()
        currentUser.login = dto.login
        currentUser.parol = dto.parol
        currentUser.fullName = dto.fullName
        repository.save(currentUser)
        return ApiResponse()
    }

    override fun deleteMe(): ApiResponse {
        repository.deleteById(authService.getCurrentUser().id!!)
        return ApiResponse()
    }
}