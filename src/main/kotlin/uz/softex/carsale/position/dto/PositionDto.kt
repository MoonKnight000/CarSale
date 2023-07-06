package uz.softex.carsale.position.dto

import uz.softex.carsale.position.entity.Permissions

class PositionDto(
    var id: Int,
    var name: String,
    var permissions: MutableList<Permissions>
)