package uz.softex.carsale.image.entity

import jakarta.persistence.Entity
import uz.softex.carsale.config.AbstractEntity

@Entity
class SaveImage(
    var path: String? = null,
    var type: String? = null,
    var name: String? = null
) : AbstractEntity()