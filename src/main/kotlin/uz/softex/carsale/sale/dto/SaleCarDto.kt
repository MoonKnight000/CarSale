package uz.softex.carsale.sale.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import uz.softex.carsale.sale.entity.SaleCar


class SaleCarDto(
    var id: Int? = null,
    @field:NotNull(message = "car shouldn`t be null")
    var car: Int? = null,
    @field:NotNull(message = "firstPayment  shouldn`t be null")
    var firstPayment: Int? = null,
    @field:Min(value = 0, message = "discount minimum  should be 0")
    @field:Max(value = 100, message = "discount maximum  should be 100")
    @field:NotNull(message = "discount value shouldn`t be null")
    var discount: Int? = null,
    var allPrice: Int? = null,
    @field:Min(value = 0, message = "monthlyPayment minimum  should be 0")
    var monthlyPayment: Int? = null,
    @field:Min(value = 0, message = "month minimum  should be 0")
    @field:NotNull(message = "month  shouldn`t be null")
    var month: Int? = null,
    var buyPerson: Int? = null,
    @field:NotNull(message = "saleCompany  shouldn`t be null")
    var saleCompany: Int? = null
) {
    constructor(saleCar: SaleCar) : this(
        saleCar.id,
        saleCar.car!!.id,
        saleCar.firstPayment,
        saleCar.discount,
        saleCar.allPrice,
        saleCar.monthlyPayment,
        saleCar.month,
        saleCar.buyPerson!!.id,
        saleCar.saleCompany!!.id
    )
}
