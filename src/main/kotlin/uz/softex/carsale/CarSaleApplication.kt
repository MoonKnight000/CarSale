package uz.softex.carsale

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
@SecurityScheme(name = "javainuseapi", scheme = "Bearer", type = SecuritySchemeType.HTTP, `in` = SecuritySchemeIn.HEADER)
@SpringBootApplication
class CarSaleApplication

fun main(args: Array<String>) {

	runApplication<CarSaleApplication>(*args)
}
