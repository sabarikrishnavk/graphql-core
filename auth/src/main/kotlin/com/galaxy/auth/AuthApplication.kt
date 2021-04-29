
package com.galaxy.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.galaxy.foundation")
class AuthApplication

fun main(args: Array<String>) {
	runApplication<AuthApplication>(*args)
}
