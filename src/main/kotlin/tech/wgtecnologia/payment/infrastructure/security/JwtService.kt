package tech.wgtecnologia.payment.infrastructure.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    @Value("\${jwt.expiration}")
    private var expiration: Long = 86400000 // 24 hours

    @Value("\${jwt.refresh-expiration}")
    private var refreshExpiration: Long = 604800000 // 7 days

    fun generateToken(userDetails: UserDetails): String {
        return buildToken(userDetails, expiration)
    }

    fun generateRefreshToken(userDetails: UserDetails): String {
        return buildToken(userDetails, refreshExpiration)
    }

    private fun buildToken(userDetails: UserDetails, expiration: Long): String {
        return Jwts.builder()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractAllClaims(token).expiration
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSignInKey() as SecretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): Key {
        return Keys.hmacShaKeyFor(secretKey.toByteArray())
    }
}