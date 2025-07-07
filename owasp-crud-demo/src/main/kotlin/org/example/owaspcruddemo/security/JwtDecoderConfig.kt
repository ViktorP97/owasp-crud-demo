package org.example.owaspcruddemo.security

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder

@Configuration
class JwtDecoderConfig(
    @Value("\${jwt.secret}") private val secret: String
) {
    @Bean
    fun jwtDecoder(): JwtDecoder {
        val keyBytes = Decoders.BASE64.decode(secret)
        val key = Keys.hmacShaKeyFor(keyBytes)
        return NimbusJwtDecoder.withSecretKey(key).build()
    }
}
