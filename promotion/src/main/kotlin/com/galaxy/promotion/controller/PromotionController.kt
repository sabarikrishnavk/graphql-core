package com.galaxy.promotion.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.Discounts
import com.galaxy.promotion.services.DiscountService
import com.galaxy.promotion.util.PromotionEventType
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
import org.apache.logging.log4j.Level
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class PromotionController(private val discountService: DiscountService , val eventLogger: EventLogger) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */

    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun discountSkusLocation(@InputArgument skuids : List<String>,@InputArgument location : String): List<Discounts> {
        eventLogger.log( PromotionEventType.PROMO_FIND, "discountSkusLocation", skuids,location)
        return discountService.discounts(skuids ,location)
    }
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun discountBySkus(@InputArgument skuids : List<String>): List<Discounts> {
        eventLogger.log( PromotionEventType.PROMO_FIND, "discountBySkus", skuids)
        return  discountService.discounts(skuids)
    }
}