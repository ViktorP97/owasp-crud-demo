package org.example.owaspcruddemo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthSuccessHandler: JwtAuthenticationSuccessHandler,
    private val jwtDecoder: JwtDecoder
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/login").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2.successHandler(jwtAuthSuccessHandler)
            }
            .oauth2ResourceServer { rs ->
                rs.jwt { spec ->
                    spec.decoder(jwtDecoder)
                }
            }

        return http.build()
    }
}