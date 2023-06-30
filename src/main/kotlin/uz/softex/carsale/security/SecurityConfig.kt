package uz.softex.carsale.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.AntPathMatcher
import uz.softex.carsale.security.filter.JWTFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(private val filter: JWTFilter) {

    @Bean
    fun configure(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/v3/**",
                    "/auth/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/api-docs/**",
                    "/api-docs/swagger-config"
//                    "/**"
                ).permitAll().anyRequest().authenticated()
            }.httpBasic { it.disable() }
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
//        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // bu har safar tokenni tekshir degaN MA`NONI BILDIRADI

        return httpSecurity.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}