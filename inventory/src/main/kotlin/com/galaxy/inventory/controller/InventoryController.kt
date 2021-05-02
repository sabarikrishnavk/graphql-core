package com.galaxy.inventory.controller

import com.galaxy.inventory.services.InventoryService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class InventoryController(private val inventoryService: InventoryService) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsQuery
//    @Secured("REGISTERED")
//    @PreAuthorize("hasAnyRole('ROLE_GUEST',)") //Will throw exception
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun inventory(@InputArgument skuid : String?,@InputArgument location : String?): List<com.galaxy.inventory.codegen.types.Inventory> {
        return if(skuid != null) {
            inventoryService.inventory().filter { it.skuid.contains(skuid) }
        } else {
            inventoryService.inventory()
        }
    }
}