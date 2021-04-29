package com.galaxy.auth.controller

import com.galaxy.auth.codegen.types.AuthPayload
import com.galaxy.auth.codegen.types.User
import com.galaxy.auth.services.UserService
import com.netflix.graphql.dgs.*

@DgsComponent
class AuthController(private val userService:   UserService) {

    @DgsMutation
    fun signup(@InputArgument email: String, @InputArgument password: String, @InputArgument username: String): AuthPayload
    {
        println("sign up")
        return userService.signup(email,password,username)?: AuthPayload("", User("","",""))
    }
    @DgsMutation
    fun signin(@InputArgument email: String, @InputArgument password: String): AuthPayload
    {
        println("sign in")
        return userService.signin(email,password)?: AuthPayload("", User("","",""))
    }
}