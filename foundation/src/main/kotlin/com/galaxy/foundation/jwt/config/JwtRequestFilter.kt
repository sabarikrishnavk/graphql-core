package com.galaxy.foundation.jwt.config


import com.galaxy.foundation.JwtUtils
import com.galaxy.foundation.constants.HEADER_AUTH
import com.galaxy.foundation.constants.HEADER_BEARER
import com.galaxy.foundation.instrumentation.GraphqlTracingInstrumentation
import com.galaxy.foundation.spring.ext.CustomUserDetails
import com.galaxy.foundation.spring.ext.CustomUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtRequestFilter : OncePerRequestFilter() {

    val LOGGER: Logger = LogManager.getLogger(GraphqlTracingInstrumentation::class.java.getName())
    @Autowired
    private val jwtUserDetailsService: CustomUserDetailsService? = null

    @Autowired
    private val jwtTokenUtil: JwtUtils? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader(HEADER_AUTH)
        var username: String? = null
        var jwtToken: String? = null
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
// only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith(HEADER_BEARER)) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil?.getUserNameFromJwtToken(jwtToken)
            } catch (e: IllegalArgumentException) {
                LOGGER.error("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                LOGGER.error("JWT Token has expired")
            }
        } else {
            LOGGER.debug("JWT Token does not begin with Bearer String")
        }

// Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val jwtUser = jwtTokenUtil?.getJwtUser(jwtToken);
            val userDetails: CustomUserDetails? = jwtUser?.let { jwtUserDetailsService!!.loadDetails(it) };

// if token is valid configure Spring Security to manually set
// authentication
            if (userDetails?.let { jwtTokenUtil?.validateToken(jwtToken, it) } == true) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
// After setting the Authentication in the context, we specify
// that the current user is authenticated. So it passes the
// Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}