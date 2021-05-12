
package com.galaxy.price

import com.galaxy.foundation.jwt.JwtProperties
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(exclude = arrayOf(SecurityAutoConfiguration::class))
@ComponentScan("com.galaxy.foundation","com.galaxy.price")
@EnableConfigurationProperties(JwtProperties::class)
class PriceApplication

fun main(args: Array<String>) {
	runApplication<PriceApplication>(*args)
}
