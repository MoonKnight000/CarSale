package uz.softex.carsale.car.entity

import jakarta.persistence.*
import uz.softex.carsale.car.dto.CarDto
import uz.softex.carsale.config.AbstractEntity
import uz.softex.carsale.model.entity.CarModel

@Entity
data class Cars(
    override var id: Int?,
    @ManyToOne(fetch = FetchType.LAZY)
    var model: CarModel? = null,
    @Enumerated(value = EnumType.STRING)
    var color: Color? = null,
    var price: Int? = null
) : AbstractEntity() {
    constructor(carDto: CarDto,model: CarModel) : this(carDto.id,model,carDto.color,carDto.price)
}
