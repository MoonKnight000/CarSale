package uz.softex.carsale.user.service

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import uz.softex.carsale.user.Users
import uz.softex.carsale.user.repository.UsersRepository

@Service
class UserDetailsServiceImp(private val repository: UsersRepository):UserDetailsService {
    override fun loadUserByUsername(username: String?): Users = repository.findByLogin(username!!).orElseGet { throw UsernameNotFoundException(username) }
}