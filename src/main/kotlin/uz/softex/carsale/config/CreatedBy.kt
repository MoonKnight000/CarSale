package uz.softex.carsale.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import uz.softex.carsale.user.Users
import java.util.*

class CreatedBy : AuditorAware<Users> {
    override fun getCurrentAuditor(): Optional<Users> {
        val authentication: Authentication? = SecurityContextHolder.getContext().getAuthentication()
        if (authentication != null && authentication.isAuthenticated && authentication.principal != "anonymousUser") {
            return Optional.of(authentication.principal as Users)
        }
        return Optional.empty<Users>()
    }
}