package uz.softex.carsale.sale

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import uz.softex.carsale.car.entity.Cars
import uz.softex.carsale.config.AbstractEntity

@Entity
data class SaleCar(
    override var id: Int? = null,
    @ManyToOne
    var car: Cars? = null,
    var firstPayment:Int? = null,
    var discount:Int? = null,
    var allPrice:Int? =null,
    var monthlyPayment:Int? = null,
    var month:Int? = null
):AbstractEntity()
