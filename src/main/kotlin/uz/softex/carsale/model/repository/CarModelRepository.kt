package uz.softex.carsale.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.model.entity.CarModel
import java.util.Optional

interface CarModelRepository : JpaRepository<CarModel, Int> {
    fun findByCompanyId(companyId: Int):List<CarModel>
    fun findByIdAndCompanyId(id: Int, companyId: Int):Optional<CarModel>
    fun existsByIdAndCompanyId(id: Int, companyId: Int):Boolean
}