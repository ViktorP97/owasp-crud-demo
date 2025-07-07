package org.example.owaspcruddemo.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String
) {
    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    private val validityInMs = 1000L * 60 * 60

    fun createToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + validityInMs)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}