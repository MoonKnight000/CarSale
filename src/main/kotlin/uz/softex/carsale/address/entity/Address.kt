package uz.softex.carsale.address.entity

import jakarta.persistence.Entity
import uz.softex.carsale.config.AbstractEntity

@Entity
data class Address(
    override var id: Int? = null,
    var region: String? = null,
    var city: String? = null,
    var street: String? = null,
    var houseNumber: Int? = null
) : AbstractEntity()
