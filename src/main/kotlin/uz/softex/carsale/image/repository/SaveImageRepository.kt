package uz.softex.carsale.image.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.image.entity.SaveImage

interface SaveImageRepository :JpaRepository<SaveImage,Int> {
}