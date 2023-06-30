package uz.softex.carsale.model.dto

import ch.qos.logback.core.model.Model
import uz.softex.carsale.model.entity.CarModel
import uz.softex.carsale.model.entity.FuelType
import uz.softex.carsale.model.entity.Transmission

class ModelDto(
    var id: Int? = null,
    var name: String? = null,
    var company: Int? = null,
    var transmission: Transmission? = null,
    var speed: Int? = null,
    var carSunroof: Boolean? = null,
    var seats: Int? = null,
    var fuelType: FuelType? = null,
    var engine: String? = null
) {
    constructor(model: CarModel) : this(
        model.id,
        model.name,
        model.company!!.id,
        model.transmission,
        model.speed,
        model.carSunroof,
        model.seats,
        model.fuelType,
        model.engine
    )
}