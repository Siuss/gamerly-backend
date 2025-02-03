package com.gamerly.projectgamerly.security

import com.gamerly.projectgamerly.exceptions.CredencialesInvalidasException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenUtils {
    @Value("\${security.secret-key}")
    lateinit var secretKey: String

    @Value("\${security.access-token-minutes}")
    var accessTokenMinutes: Int = 60

    private val logger = LoggerFactory.getLogger(TokenUtils::class.java)

    /**
     * Crea un token JWT sin roles, solo con el nombre de usuario
     */
    fun createToken(nombre: String, id: Long): String {
        val expirationTimeMillis = accessTokenMinutes * 60 * 1000  // Corrección en cálculo de tiempo
        val now = Date()

        return Jwts.builder()
            .subject(nombre)
            .claim("id", id)
            .issuedAt(now)
            .expiration(Date(now.time + expirationTimeMillis))
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .compact()
    }

    /**
     * Valida y extrae el usuario del token
     */
    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val secret = Keys.hmacShaKeyFor(secretKey.toByteArray())
        val claims = Jwts.parser()
            .verifyWith(secret)
            .build()
            .parseSignedClaims(token)
            .payload

        // Verificar que el token no esté expirado
        val expiration = claims.expiration
        if (expiration.before(Date())) {
            throw CredencialesInvalidasException()
        }

        val username = claims.subject ?: throw CredencialesInvalidasException()

        logger.info("Token decoded, user: $username")

        return UsernamePasswordAuthenticationToken(username, null, emptyList()) // Sin roles
    }
}
