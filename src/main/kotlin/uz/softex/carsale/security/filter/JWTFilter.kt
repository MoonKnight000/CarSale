package uz.softex.carsale.security.filter


import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import uz.softex.carsale.user.exception.UserNotEnabled
import uz.softex.carsale.user.service.UserDetailsServiceImp
import java.util.HashSet

@Component
class JWTFilter(private val provider: JWTProvider, val authservice: UserDetailsServiceImp) :
    OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
            var header = request.getHeader("Authorization")
            println(header)
            if (header != null) {
                header = header.substring(7)
                if (provider.validateToken(header)) {
                    val usernameFromToken = provider.getUsernameFromToken(header)
                    val loadUserByUsername = authservice.loadUserByUsername(usernameFromToken)
                    if(!loadUserByUsername.enabled) throw UserNotEnabled()
                    val usernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(loadUserByUsername,
                            null,
                            loadUserByUsername.authorities)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
            filterChain.doFilter(request, response)
//        }
    }
}