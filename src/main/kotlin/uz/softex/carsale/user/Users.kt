package uz.softex.carsale.user

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import uz.softex.carsale.company.entity.Company
import uz.softex.carsale.config.AbstractEntity
import uz.softex.carsale.position.entity.Position
import java.util.HashSet

@Entity
data class Users(
    override var id: Int? = null,
    var login: String? = null,
    var parol: String? = null,
    var fullName: String? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    var position: Position? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var workCompany: Company? = null,
    var enabled: Boolean = false
) : AbstractEntity(), UserDetails {
    //    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        TODO("Not yet implemented")
//    }
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        println(position)
        if (position != null) {
            val grantedAuthorities = HashSet<GrantedAuthority>()
            position!!.permissions!!.forEach {
                grantedAuthorities.add(SimpleGrantedAuthority(it.name))
            }
            return grantedAuthorities
        }
        return mutableListOf()
    }

    override fun getPassword(): String = parol!!

    override fun getUsername(): String = login!!

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = enabled
}
