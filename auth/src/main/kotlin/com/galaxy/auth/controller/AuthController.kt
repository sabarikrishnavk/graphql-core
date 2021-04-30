package com.galaxy.auth.controller

import com.galaxy.auth.codegen.types.AuthPayload
import com.galaxy.auth.services.UserService
import com.netflix.graphql.dgs.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@DgsComponent
class AuthController(private val userService:   UserService , private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @DgsMutation
    fun signup(@InputArgument email: String, @InputArgument password: String, @InputArgument username: String): AuthPayload
    {
        println("sign up")
        return userService.signup(email,bCryptPasswordEncoder.encode(password),username)?: AuthPayload()
    }
    @DgsMutation
    fun signin(@InputArgument email: String, @InputArgument password: String): AuthPayload
    {
        println("sign in")
        return userService.signin(email,bCryptPasswordEncoder.encode(password))?: AuthPayload()
    }
}