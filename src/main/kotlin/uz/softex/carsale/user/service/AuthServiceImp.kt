package uz.softex.carsale.user.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.position.exception.PositionNotFound
import uz.softex.carsale.position.repository.PositionRepository
import uz.softex.carsale.security.filter.JWTProvider
import uz.softex.carsale.user.Users
import uz.softex.carsale.user.dto.SignIn
import uz.softex.carsale.user.dto.UsersDto
import uz.softex.carsale.user.exception.UserExists
import uz.softex.carsale.user.exception.UserNotFound
import uz.softex.carsale.user.repository.UsersRepository

@Service
class AuthServiceImp(
    private val repository: UsersRepository,
    private val filter: JWTProvider,
    private val encoder: PasswordEncoder,
    private val positionRepository: PositionRepository
) :
    AuthService {
    override fun signUp(dto: UsersDto): ApiResponse {
        val users = Users()
        users.fullName = dto.fullName
        if (repository.existsByLogin(dto.login!!))
            throw UserExists()
        users.login = dto.login
        users.parol = encoder.encode(dto.parol)
        users.position = positionRepository.findById(3).orElseThrow { throw PositionNotFound() }
        repository.save(users)
        return ApiResponse()
    }

    override fun signIn(dto: SignIn): ApiResponseGeneric<String> {
        println(dto)
        val user = repository.findByLogin(dto.login).orElseThrow { throw UserNotFound() }
        if (!encoder.matches(dto.parol, user.password)) throw UserNotFound()
        return ApiResponseGeneric(filter.generateToken(user.login!!))
    }

    override fun getCurrentUser(): Users {
        return SecurityContextHolder.getContext().authentication.principal as Users
    }
}