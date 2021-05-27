package com.galaxy.price.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.price.codegen.types.Price
import com.galaxy.price.codegen.types.Sku
import com.galaxy.price.services.PriceService
import com.galaxy.price.util.PriceEventType
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
import org.apache.logging.log4j.Level
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class PriceController(private val priceService: PriceService , val eventLogger: EventLogger ) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsEntityFetcher(name ="Sku")
    fun skus( values : Map<String,String> ): Sku?{
        return values.get("skuid")?.let { Sku(it) }
    }
    @DgsData(parentType = "Sku" , field="prices")
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun skuprice(dfe: DataFetchingEnvironment): List<Price> {
        val sku = dfe.getSource<Sku>();
        return  priceService.price(sku.skuid)
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun priceSkusLocation(@InputArgument skuids : List<String>,@InputArgument location : String): List<Price> {

        eventLogger.log(Level.INFO,"priceSkuLocation",PriceEventType.PRICE_SAVE, skuids,location)
        return priceService.price(skuids,location)
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun priceBySkus(@InputArgument skuids : List<String> ): List<Price> {
        eventLogger.log(Level.INFO,"priceBySku",PriceEventType.PRICE_SAVE, skuids)
        return priceService.price(skuids)
    }
}