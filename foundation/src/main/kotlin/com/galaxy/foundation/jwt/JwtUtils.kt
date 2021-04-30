package com.galaxy.foundation

import com.galaxy.foundation.jwt.JwtProperties
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class JwtUtils (private val jwtProperties: JwtProperties){

    private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)

    fun getUserNameFromJwtToken(token: String?): String? {
        return Jwts.parser().setSigningKey(jwtProperties.secretkey).parseClaimsJws(token).getBody().getSubject()
    }
    fun validateJwtToken(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtProperties.secretkey).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }
}

