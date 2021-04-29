
package com.galaxy.auth.services

import org.springframework.stereotype.Service

interface UserService {
    fun users(): List<com.galaxy.auth.codegen.types.User>
}

@Service
class BasicUserService : UserService {
    override fun users(): List<com.galaxy.auth.codegen.types.User> {
        return listOf(
//            com.galaxy.catalog.codegen.types.Sku(id = 1, title = "Stranger Things", releaseYear = 2016),
        )
    }
}