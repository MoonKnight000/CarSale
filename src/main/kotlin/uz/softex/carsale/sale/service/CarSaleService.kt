package uz.softex.carsale.sale.service

import jakarta.servlet.http.HttpServletResponse
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.sale.dto.SaleCarDto

interface CarSaleService {
    fun getAllSales(): ApiResponseGeneric<*>
    fun getSalesById(id: Int): ApiResponseGeneric<*>
    fun saleCar(dto: SaleCarDto): ApiResponse
    fun update(dto: SaleCarDto): ApiResponse
    fun delete(id: Int): ApiResponse
    fun contractToPdf(id: Int, response: HttpServletResponse): ApiResponse
    fun getMyContracts(): ApiResponseGeneric<*>
}