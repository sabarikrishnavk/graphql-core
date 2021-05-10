package com.galaxy.price.controller

import com.galaxy.price.codegen.types.Price
import com.galaxy.price.codegen.types.Sku
import com.galaxy.price.services.PriceService
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class PriceController(private val priceService: PriceService ) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsEntityFetcher(name ="Sku")
    fun skus( values : Map<String,String> ): Sku?{
        return values.get("skuid")?.let { Sku(it) }
    }
    @DgsData(parentType = "Sku" , field="price")
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun skuprice(dfe: DataFetchingEnvironment): List<Price> {
        val sku = dfe.getSource<Sku>();
        return if(sku.skuid != null) {
            priceService.price().filter { it.skuid.contains(sku.skuid) }
        } else {
            priceService.price()
        }
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun priceSkuLocation(@InputArgument skuid : String?,@InputArgument location : String?): List<Price> {
        return if(skuid != null) {
            priceService.price().filter { it.skuid.contains(skuid) }
        } else {
            priceService.price()
        }
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun priceBySku(@InputArgument skuid : String? ): List<Price> {
        return if(skuid != null) {
            priceService.price().filter { it.skuid.contains(skuid) }
        } else {
            priceService.price()
        }
    }
}