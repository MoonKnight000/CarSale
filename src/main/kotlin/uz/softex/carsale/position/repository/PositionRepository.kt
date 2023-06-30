package uz.softex.carsale.position.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.position.entity.Position

interface PositionRepository : JpaRepository<Position, Int> {
}