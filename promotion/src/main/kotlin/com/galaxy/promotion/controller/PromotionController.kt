package com.galaxy.promotion.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.*
import com.galaxy.promotion.services.DiscountService
import com.galaxy.promotion.util.PromotionEventType
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
import org.apache.logging.log4j.Level
import org.springframework.beans.BeanUtils
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

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


    @DgsMutation
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun addPromotion(inputPromotion: InputPromotion, dfe: DataFetchingEnvironment?): Promotion {
        eventLogger.log( PromotionEventType.PROMO_FIND, "addPromotion", inputPromotion)

        val promotion = create(inputPromotion)
        return  discountService.savePromotions(promotion)
    }


    @DgsMutation
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun updatePromotion(inputPromotion: InputPromotion, dfe: DataFetchingEnvironment?): Promotion {
        eventLogger.log( PromotionEventType.PROMO_FIND, "updatePromotion", inputPromotion)

        val promotion = create(inputPromotion)
        return  discountService.savePromotions(promotion)
    }

    private fun create(inputPromotion: InputPromotion): Promotion {
        val conditions = inputPromotion.conditions.map{
            PromotionCondition(field = it!!.field,value = it!!.value, operator = Operator.valueOf(it.operator))
        }

        val promotion = Promotion(
            promotionid= UUID.randomUUID().toString(),
            promotionType = PromotionType.valueOf(inputPromotion.promotionType),
            status = PromotionStatus.valueOf(inputPromotion.status),
            discount = inputPromotion.discount,
            stackable = inputPromotion.stackable,
            rank = inputPromotion.rank,
            affectedskus =  inputPromotion.affectedskus,

            startdate = inputPromotion.startdate,
            enddate = inputPromotion.enddate,
            conditions = conditions,

            cartdesc = inputPromotion.cartdesc,
            plpdesc = inputPromotion.plpdesc,
            pdpdesc = inputPromotion.pdpdesc,
            admindesc = inputPromotion.admindesc



        )
        return promotion
    }
}