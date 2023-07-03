package uz.softex.carsale.sale.dto

import uz.softex.carsale.sale.entity.SaleCar


class SaleCarDto(
    var id: Int? = null,
    var car: Int? = null,
    var firstPayment: Int? = null,
    var discount: Int? = null,
    var allPrice: Int? = null,
    var monthlyPayment: Int? = null,
    var month: Int? = null,
    var buyPerson: Int? = null,
    var saleCompany :Int? = null
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
