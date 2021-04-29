
package com.galaxy.auth.services

import com.galaxy.auth.codegen.types.AuthPayload
import com.galaxy.auth.codegen.types.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.impl.TextCodec
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*


interface UserService {
    fun user(userId: String): User
    fun signup(email: String, password: String, username: String): AuthPayload?
    fun signin(email: String, password: String): AuthPayload?
}

@Service
class BasicUserService : UserService {
    override fun user(userId: String): User {
        return User(userId,"user","some@galaxy.com" )
    }
    override fun signup(email: String, password: String, username: String): AuthPayload? {
        val user = User(UUID.randomUUID().toString(),username,email)

        val token = Jwts.builder()
            .setIssuer("Stormpath")
            .setSubject("msilverman")
            .claim("name", "Micah Silverman")
            .claim("scope", "admins")
            .setIssuedAt(Date())
            .compact()
        return AuthPayload(token,user)
    }
    override fun signin(email: String, password: String ): AuthPayload? {
        val user = User(UUID.randomUUID().toString(),"",email)

        val token = Jwts.builder()
            .setIssuer("Stormpath")
            .setSubject("msilverman")
            .claim("name", "Micah Silverman")
            .claim("scope", "admins")
            .setIssuedAt(Date())
            .compact()
        return AuthPayload(token,user)
    }
}