package uz.softex.carsale.user.repository;

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.company.entity.Company
import uz.softex.carsale.user.Users
import java.util.Optional

interface UsersRepository : JpaRepository<Users, Int> {
    fun findByLogin(login: String): Optional<Users>
    fun existsByLogin(login: String): Boolean
    fun findByWorkCompanyId(id: Int): List<Users>
    fun findByIdAndWorkCompanyId(id: Int, workCompanyId: Int):Optional<Users>
}