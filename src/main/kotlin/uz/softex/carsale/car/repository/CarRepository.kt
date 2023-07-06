package uz.softex.carsale.car.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.car.entity.Cars
import java.util.Optional

interface CarRepository : JpaRepository<Cars, Int> {
    fun findByModelCompanyId(modelCompanyId: Int): List<Cars>
    fun findByIdAndModelCompanyId(id: Int, modelCompanyId: Int): Optional<Cars>
    fun existsByIdAndModelCompanyId(id: Int, modelCompanyId: Int):Boolean
}