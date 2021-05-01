package com.galaxy.foundation.jwt

data class JwtUser(val userId: String?, val name: String?, val email: String,val roles: List<UserType>?){

}