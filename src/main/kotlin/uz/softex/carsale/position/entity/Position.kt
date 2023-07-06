package uz.softex.carsale.position.entity

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import uz.softex.carsale.config.AbstractEntity

@Entity
data class Position(
    override var id: Int? = null,
    var name:String? = null,
    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    var permissions: MutableList<Permissions>?=null
) : AbstractEntity()
