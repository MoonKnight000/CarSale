package uz.softex.carsale.user.service

import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.Users
import uz.softex.carsale.user.dto.SignIn
import uz.softex.carsale.user.dto.UsersDto

interface AuthService {
    fun signUp(dto: UsersDto): ApiResponse//ro`yxatdan o`tish
    fun signIn(dto: SignIn): ApiResponseGeneric<String>//tizimga kirish
    fun getCurrentUser(): Users
}