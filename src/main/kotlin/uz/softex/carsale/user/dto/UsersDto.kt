package uz.softex.carsale.user.dto

import uz.softex.carsale.user.Users

class UsersDto(
    var id: Int?,
    var fullName: String?,
    var login: String?,
    var parol: String?,
    var position: Int?,
    var workCompany: Int?
) {
    constructor(users: Users) : this(
        users.id,
        users.fullName,
        users.login,
        users.parol,
        users.position!!.id,
        users.workCompany!!.id
    )
}
