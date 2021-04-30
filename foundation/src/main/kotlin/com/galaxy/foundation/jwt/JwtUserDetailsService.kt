package com.galaxy.foundation.jwt

import com.galaxy.foundation.jwt.JwtUser
import com.galaxy.foundation.jwt.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class JwtUserDetailsService: UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return CustomUserDetails(username, mutableListOf("user"))
    }
    fun loadDetails(jwtUser: JwtUser): UserDetails{
        return CustomUserDetails(jwtUser.email, jwtUser.roles)
    }
}

