package uz.softex.carsale.sale.repository;

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.sale.entity.SaleCar

interface SaleCarRepository : JpaRepository<SaleCar, Int> {
    fun findByBuyPersonId(buyPersonId: Int):List<SaleCar>
}