
package com.galaxy.catalog

import com.galaxy.foundation.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(exclude = arrayOf(SecurityAutoConfiguration::class))
@ComponentScan("com.galaxy.foundation","com.galaxy.price")
@EnableConfigurationProperties(JwtProperties::class)
class InventoryApplication

fun main(args: Array<String>) {
	runApplication<InventoryApplication>(*args)
}
