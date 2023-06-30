package uz.softex.carsale.car.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.car.entity.Cars

interface CarRepository:JpaRepository<Cars,Int> {
}