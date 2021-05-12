
package com.galaxy.promotion

import com.galaxy.foundation.jwt.JwtProperties

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(exclude = arrayOf(SecurityAutoConfiguration::class))
@ComponentScan("com.galaxy.foundation","com.galaxy.promotion")
@EnableConfigurationProperties(JwtProperties::class)
class PromotionApplication

fun main(args: Array<String>) {
	val LOGGER: Logger = LogManager.getLogger(PromotionApplication::class.java.getName())

	LOGGER.info("test message")
	runApplication<PromotionApplication>(*args)
}
