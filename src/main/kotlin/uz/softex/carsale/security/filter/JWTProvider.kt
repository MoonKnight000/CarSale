package uz.softex.carsale.security.filter

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTProvider {
    private val securityKey = "sdfghjkhgfdgcvbnhgfcvb"

    fun generateToken(username: String): String {
        println(username)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 36000000L))
            .signWith(SignatureAlgorithm.HS512, securityKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(securityKey)
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String? {

        return Jwts.parser()
            .setSigningKey(securityKey)
            .parseClaimsJws(token)
            .body
            .subject
    }
}