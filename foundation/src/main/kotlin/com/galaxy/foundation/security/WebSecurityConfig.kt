package com.galaxy.foundation.security

import com.galaxy.foundation.jwt.config.JwtAuthenticationEntryPoint
import com.galaxy.foundation.jwt.config.JwtRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


open class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint? = null

    @Autowired
    val jwtUserDetailsService: UserDetailsService? = null

    @Autowired
    val jwtRequestFilter: JwtRequestFilter? = null
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    open override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {

// We don't need CSRF for this example
        httpSecurity.csrf().disable()// dont authenticate this particular request
            .authorizeRequests()
            .antMatchers("/graphiql").permitAll()
            .antMatchers("/actuator").permitAll()
            .antMatchers("/actuator/*").permitAll()
            .antMatchers("/graphql").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

// Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}