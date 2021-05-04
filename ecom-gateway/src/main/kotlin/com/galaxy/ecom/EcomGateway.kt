
package com.galaxy.catalog

import com.galaxy.foundation.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication//(exclude = arrayOf(SecurityAutoConfiguration::class))
@ComponentScan("com.galaxy.foundation","com.galaxy.ecom")
@EnableConfigurationProperties(JwtProperties::class)
class EcomGateway

fun main(args: Array<String>) {
	runApplication<EcomGateway>(*args)
}
