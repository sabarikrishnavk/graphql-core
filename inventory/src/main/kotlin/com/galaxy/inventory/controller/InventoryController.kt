package com.galaxy.inventory.controller

import com.galaxy.inventory.codegen.types.Inventory
import com.galaxy.inventory.codegen.types.Sku
import com.galaxy.inventory.services.InventoryService
import com.netflix.graphql.dgs.*
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class InventoryController(private val inventoryService: InventoryService) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsEntityFetcher(name ="Sku")
    fun skus( values : Map<String,String> ): Sku?{
        return values.get("skuid")?.let { Sku(it) }
    }
    @DgsQuery
//    @Secured("REGISTERED")
//    @PreAuthorize("hasAnyRole('ROLE_GUEST',)") //Will throw exception
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun inventorySkuLocation(@InputArgument skuid : String?,@InputArgument location : String?): List<Inventory> {
        return if(skuid != null) {
            inventoryService.inventory().filter { it.skuid.contains(skuid) }
        } else {
            inventoryService.inventory()
        }
    }
    @DgsData(parentType = "Sku" , field="inventory")
//    @Secured("REGISTERED")
//    @PreAuthorize("hasAnyRole('ROLE_GUEST',)") //Will throw exception
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun inventory(@InputArgument skuid : String? ): List<Inventory> {
        return if(skuid != null) {
            inventoryService.inventory().filter { it.skuid.contains(skuid) }
        } else {
            inventoryService.inventory()
        }
    }
}