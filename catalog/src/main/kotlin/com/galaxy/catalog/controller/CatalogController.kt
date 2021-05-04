package com.galaxy.catalog.controller

import com.galaxy.catalog.services.SkuService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class CatalogController(private val showsService: SkuService) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsQuery
//    @Secured("REGISTERED")
//    @PreAuthorize("hasAnyRole('ROLE_GUEST',)") //Will throw exception
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun skus(@InputArgument skuid : String?): List<com.galaxy.catalog.codegen.types.Sku> {
        return if(skuid != null) {
            showsService.skus().filter { it.skuid.contains(skuid) }
        } else {
            showsService.skus()
        }
    }
}