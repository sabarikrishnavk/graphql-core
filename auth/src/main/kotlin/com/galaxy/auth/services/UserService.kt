
package com.galaxy.auth.services

import com.galaxy.auth.codegen.types.AuthPayload
import com.galaxy.auth.codegen.types.User
import com.galaxy.foundation.jwt.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService (private val jwtProperties: JwtProperties) {

    fun user(userId: String): User {
        return User(userId,"user","some@galaxy.com" )
    }
    fun signup(email: String, password: String, username: String): AuthPayload? {
        val user = User(UUID.randomUUID().toString(),username,email)
        val role = "user"

        val token = Jwts.builder()
            .setIssuer("Galaxy")
            .setSubject(username)
            .claim("name", username)
            .claim("scope", user)
            .setIssuedAt(Date())
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secretkey)
            .compact()
        return AuthPayload(token,user)
    }
     fun signin(email: String, password: String ): AuthPayload? {

         val username = email;
         val role = "user"

        val user = User(UUID.randomUUID().toString(),username,email)
        val token = Jwts.builder()
            .setIssuer("Galaxy")
            .setSubject(username)
            .claim("name", username)
            .claim("scope", role)
            .setIssuedAt(Date())
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secretkey)
            .compact()
        return AuthPayload(token,user)
    }
}