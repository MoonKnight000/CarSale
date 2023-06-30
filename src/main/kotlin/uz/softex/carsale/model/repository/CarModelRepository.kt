package uz.softex.carsale.model.repository;

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.model.entity.CarModel

interface CarModelRepository : JpaRepository<CarModel, Int> {
    fun findByCompanyId(companyId: Int):List<CarModel>
}