package uz.softex.carsale.handler

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import uz.softex.carsale.car.exception.CarCountIsZero
import uz.softex.carsale.car.exception.CarNotFound
import uz.softex.carsale.company.exception.CompanyNotFound
import uz.softex.carsale.model.exception.CarModelNotFound
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.position.exception.PositionNotFound
import uz.softex.carsale.sale.exception.ContractNotFound
import uz.softex.carsale.sale.exception.FirstPaymentIsALot
import uz.softex.carsale.sale.exception.PhotoNotFound
import uz.softex.carsale.user.exception.UserExists
import uz.softex.carsale.user.exception.UserNotEnabled
import uz.softex.carsale.user.exception.UserNotFound

@ControllerAdvice
class Handler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(UserNotFound::class)
    fun handleUserNotFound(e: UserNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("User Not Found", false))
    }

    @ExceptionHandler(PositionNotFound::class)
    fun handlePositionNotFound(e: PositionNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("Position Not Found", false))
    }

    @ExceptionHandler(UserExists::class)
    fun handleUserExists(e: UserExists): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("User exists", false))
    }

    @ExceptionHandler(UserNotEnabled::class)
    fun handleUserNotEnabled(e: UserNotEnabled): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("User Not Enable", false))
    }

    @ExceptionHandler(CompanyNotFound::class)
    fun handleCompanyNotFound(e: CompanyNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("Company Not Found", false))
    }

    @ExceptionHandler(CarNotFound::class)
    fun handleCarNotFound(e: CarNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("Car Not Found", false))
    }

    @ExceptionHandler(CarCountIsZero::class)
    fun handleCarCountIsZero(e: CarCountIsZero): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("Car count is zero", false))
    }

    @ExceptionHandler(ContractNotFound::class)
    fun handleContractNotFound(e: ContractNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("contract not found", false))
    }

    @ExceptionHandler(CarModelNotFound::class)
    fun handleCarModelNotFound(e: CarModelNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("model not found", false))
    }

    @ExceptionHandler(PhotoNotFound::class)
    fun handlePhotoNotFound(e: PhotoNotFound): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(404).body(ApiResponse("photo not found", false))
    }

    @ExceptionHandler(FirstPaymentIsALot::class)
    fun handleFirstPaymentIsALot(e: FirstPaymentIsALot): ResponseEntity<ApiResponse> {

        return ResponseEntity.status(404).body(ApiResponse(e.string, false))
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        println("bu yerga keldi")
        println(ex)
        println(headers)
        println(request)
        val allErrors = ex.bindingResult.allErrors
        val error = mutableListOf<String>()
        allErrors.forEach { error.add(it.defaultMessage.toString()) }
        return ResponseEntity.status(400).body(error)
    }
}

