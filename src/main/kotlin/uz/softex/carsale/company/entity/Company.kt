package uz.softex.carsale.company.entity

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import uz.softex.carsale.address.entity.Address
import uz.softex.carsale.config.AbstractEntity
import uz.softex.carsale.image.entity.SaveImage

@Entity
data class Company(
    override var id: Int? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    @OneToMany
    var address: MutableList<Address>? = null,
    @OneToOne
    var  image:SaveImage?=null
) : AbstractEntity()