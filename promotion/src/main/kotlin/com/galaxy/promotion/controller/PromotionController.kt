package com.galaxy.promotion.controller

import com.galaxy.promotion.codegen.types.Discounts
import com.galaxy.promotion.codegen.types.Price
import com.galaxy.promotion.services.DiscountService
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class PromotionController(private val discountService: DiscountService  ) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsEntityFetcher(name ="Price")
    fun price( values : Map<String,String> ): Price?{
        return values.get("priceId")?.let { Price(it) }
    }
    @DgsData(parentType = "Price" , field="discounts")
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun skuprice(dfe: DataFetchingEnvironment): List<Discounts> {
        val price = dfe.getSource<Price>();
        return if(price.priceid != null) {
            discountService.discounts().filter { it.priceid.contains(price.priceid) }
        } else {
            discountService.discounts()
        }
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun discountSkuLocation(@InputArgument skuid : String?,@InputArgument location : String?): List<Discounts> {
        return if(skuid != null) {
            discountService.discounts().filter { it.skuid.contains(skuid) }
        } else {
            discountService.discounts()
        }
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun discountBySku(@InputArgument skuid : String? ): List<Discounts> {
        return if(skuid != null) {
            discountService.discounts().filter { it.skuid.contains(skuid) }
        } else {
            discountService.discounts()
        }
    }
}