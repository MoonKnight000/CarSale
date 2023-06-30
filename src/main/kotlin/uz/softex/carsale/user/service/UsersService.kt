package uz.softex.carsale.user.service

import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.Users
import uz.softex.carsale.user.dto.UsersDto

interface UsersService {
    fun enableUsers(id: Int, enable: Boolean): ApiResponse
    fun getAllUsersByCompany():ApiResponseGeneric<List<Users>>
    fun getUserById(id:Int):ApiResponseGeneric<Users>
    fun updateUser(dto: UsersDto):ApiResponse
    fun updatePositionOfUser(dto:UsersDto):ApiResponse
    fun updateMe(dto:UsersDto):ApiResponse
    fun deleteUser(id:Int):ApiResponse
    fun deleteMe():ApiResponse
}