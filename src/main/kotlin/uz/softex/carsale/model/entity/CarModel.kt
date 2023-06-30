package uz.softex.carsale.model.entity

import jakarta.persistence.*
import uz.softex.carsale.company.entity.Company
import uz.softex.carsale.config.AbstractEntity
import uz.softex.carsale.model.dto.ModelDto

@Entity
data class CarModel(
    override var id: Int? = null,
    var name: String? = null,
    @ManyToOne
    var company: Company? = null,
    @Enumerated(value = EnumType.STRING)
    var transmission: Transmission? = null,
    var speed: Int? = null,
    var carSunroof: Boolean? = null,
    var seats: Int? = null,
    @Enumerated(value = EnumType.STRING)
    var fuelType: FuelType? = null,
    var engine: String? = null
) : AbstractEntity() {
    constructor(modelDto: ModelDto, company: Company?) : this(
        modelDto.id,
        modelDto.name,
        company,
        modelDto.transmission,
        modelDto.speed,
        modelDto.carSunroof,
        modelDto.seats,
        modelDto.fuelType,
        modelDto.engine
    )
}
