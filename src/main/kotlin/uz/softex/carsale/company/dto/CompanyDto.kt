package uz.softex.carsale.company.dto

import uz.softex.carsale.company.entity.Company

class CompanyDto(
    var id: Int? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var address: List<Int>? = null,
    var image: Int? = null
) {
    constructor(company: Company) : this(
        company.id,
        company.name,
        company.phoneNumber,
        company.address?.map { it.id!! },
        company.image!!.id
    )
}
