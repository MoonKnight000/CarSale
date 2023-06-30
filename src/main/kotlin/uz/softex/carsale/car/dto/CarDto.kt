package uz.softex.carsale.car.dto

import uz.softex.carsale.car.entity.Cars
import uz.softex.carsale.car.entity.Color

class CarDto(
    var id: Int? = null,
    var carModel: Int? = null,
    var color: Color? = null,
    var price: Int? = null
) {
    constructor(car: Cars) : this(car.id, car.model!!.id, car.color, car.price)
}
