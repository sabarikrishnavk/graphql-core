
package com.galaxy.auth

import com.galaxy.foundation.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication (exclude = arrayOf(SecurityAutoConfiguration::class))
@ComponentScan("com.galaxy.foundation","com.galaxy.auth")
@EnableConfigurationProperties(JwtProperties::class)
class AuthApplication
fun main(args: Array<String>) {
	runApplication<AuthApplication>(*args)
}


