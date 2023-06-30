package uz.softex.carsale.payload

class ApiResponseGeneric<T>(
    var message: String = "successfully",
    var success: Boolean = true,
    var data: T
) {
    constructor(data: T) : this("successfully",true,data)
}