package uz.softex.carsale.car.entity

import jakarta.persistence.*
import uz.softex.carsale.config.AbstractEntity
import uz.softex.carsale.image.entity.SaveImage
import uz.softex.carsale.model.entity.CarModel
import java.time.LocalDate

@Entity
data class Cars(
    override var id: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var model: CarModel? = null,
    @Enumerated(value = EnumType.STRING)
    var color: Color? = null,
    var price: Int? = null,
    var count: Int? = null,
    @OneToMany
    var images: MutableList<SaveImage> = mutableListOf(),
    var dateOfProduced: LocalDate = LocalDate.now(),
    var used:Boolean = false
) : AbstractEntity()
