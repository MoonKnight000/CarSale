package uz.softex.carsale.company.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.company.entity.Company

interface CompanyRepository : JpaRepository<Company, Int>