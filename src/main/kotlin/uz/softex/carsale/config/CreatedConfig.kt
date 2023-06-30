package uz.softex.carsale.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import uz.softex.carsale.user.Users

@Configuration
@EnableJpaAuditing
class CreatedConfig {
    @Bean
    open fun auditorAware(): AuditorAware<Users>? {
        return CreatedBy()
    }
}