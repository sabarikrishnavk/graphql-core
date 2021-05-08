package com.galaxy.inventory.controller

import com.galaxy.inventory.codegen.types.Inventory
import com.galaxy.inventory.codegen.types.Sku
import com.galaxy.inventory.services.InventoryService
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
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
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun inventorySkuLocation(@InputArgument skuid : String?,@InputArgument location : String?): List<Inventory> {
        return if(skuid != null) {
            inventoryService.inventory().filter { it.skuid.contains(skuid) }
        } else {
            inventoryService.inventory()
        }
    }
    @DgsData(parentType = "Sku" , field="inventory")
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun inventory(dfe: DataFetchingEnvironment): List<Inventory> {
        val sku = dfe.getSource<Sku>();
        return if(sku.skuid != null) {
            inventoryService.inventory().filter { it.skuid.contains(sku.skuid) }
        } else {
            inventoryService.inventory()
        }
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun inventoryBySku(@InputArgument skuid : String? ): List<Inventory> {
        return if(skuid != null) {
            inventoryService.inventory().filter { it.skuid.contains(skuid) }
        } else {
            inventoryService.inventory()
        }
    }
}