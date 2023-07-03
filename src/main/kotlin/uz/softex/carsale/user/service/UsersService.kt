package uz.softex.carsale.user.service

import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.user.Users
import uz.softex.carsale.user.dto.UsersDto

interface UsersService {
    fun getAllUsersByCompany():ApiResponseGeneric<*>
    fun getUserById(id:Int):ApiResponseGeneric<*>
    fun updateUser(dto: UsersDto):ApiResponse
    fun updatePositionOfUser(dto:UsersDto):ApiResponse
    fun updateMe(dto:UsersDto):ApiResponse
    fun deleteUser(id:Int):ApiResponse
    fun deleteMe():ApiResponse
    fun addUser(dto: UsersDto):ApiResponse
}