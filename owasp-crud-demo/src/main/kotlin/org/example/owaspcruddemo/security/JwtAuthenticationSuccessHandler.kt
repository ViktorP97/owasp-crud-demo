package org.example.owaspcruddemo.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component


@Component
class JwtAuthenticationSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider
): AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oauth = authentication as OAuth2AuthenticationToken
        val username = oauth.principal.getAttribute<String>("login") ?: oauth.name

        val jwt = jwtTokenProvider.createToken(username)
        response.contentType = "application/json"
        response.writer.write("""{"token":"$jwt"}""")
    }
}