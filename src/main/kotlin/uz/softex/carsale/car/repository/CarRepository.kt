package uz.softex.carsale.car.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import uz.softex.carsale.car.entity.Cars
import uz.softex.carsale.company.entity.Company
import java.util.Optional

interface CarRepository : JpaRepository<Cars, Int> {
    fun findByModelCompanyId(modelCompanyId: Int): List<Cars>
    fun findByIdAndModelCompanyId(id: Int, modelCompanyId: Int): Optional<Cars>
    fun findAllByModelCompanyId(modelCompanyId: Int): List<Cars>
    fun existsByIdAndModelCompanyId(id: Int, modelCompanyId: Int):Boolean
}