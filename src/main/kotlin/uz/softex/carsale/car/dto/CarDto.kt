package uz.softex.carsale.car.dto

import uz.softex.carsale.car.entity.Cars
import uz.softex.carsale.car.entity.Color
import java.time.LocalDate

class CarDto(
    var id: Int? = null,
    var carModel: Int? = null,
    var color: Color? = null,
    var price: Int? = null,
    var count: Int? = null,
    var images: List<Int> = mutableListOf(),
    var dateOfProduced: LocalDate =  LocalDate.now(),
    var used:Boolean = false
) {
    constructor(car: Cars) : this(car.id, car.model!!.id, car.color, car.price, car.count,car.images.map { it.id!! },car.dateOfProduced,car.used)
}
