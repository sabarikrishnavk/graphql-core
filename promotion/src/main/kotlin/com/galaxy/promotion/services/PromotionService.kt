package com.galaxy.promotion.services

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.Operator
import com.galaxy.promotion.codegen.types.PromotionCondition
import com.galaxy.promotion.codegen.types.PromotionType
import com.galaxy.promotion.engine.objects.*
import com.galaxy.promotion.util.PromotionEventType
import com.google.gson.Gson
import org.springframework.stereotype.Service
import java.util.*

@Service
class PromotionService( val eventLogger: EventLogger , val discountService: DiscountService){
    val gson = Gson()

    fun activeRules():MutableList<PERule>{

        val promotions = discountService.getActivePromotions()

        val rules = mutableListOf<PERule>()
        promotions.forEach {
            val rule = PERule()
            rule.conditions = it.conditions
            rule.action = PEAction(PEDiscount(it.promotionid,it.discount))
            rule.requestClassName = when (it.promotionType.name) {

                PromotionType.ORDER_PERCENT_OFF.name -> PEOrderRequest::class.java.name
                PromotionType.ORDER_AMOUNT_OFF.name -> PEOrderRequest::class.java.name
                PromotionType.ORDER_FREE_GIFT.name -> PEOrderRequest::class.java.name

                PromotionType.SHIPPING_FIXED_PRICE.name -> PEOrderRequest::class.java.name
                PromotionType.SHIPPING_AMOUNT_OFF.name -> PEOrderRequest::class.java.name

                PromotionType.SHIPPING_SKU_FIXED_PRICE.name -> PESkuRequest::class.java.name
                PromotionType.SKU_TOTAL_PERCENT_OFF.name -> PESkuRequest::class.java.name
                PromotionType.SKU_TOTAL_AMOUNT_OFF.name -> PESkuRequest::class.java.name
                PromotionType.SKU_TOTAL_FIXED_PRICE.name -> PESkuRequest::class.java.name

                PromotionType.CROSS_SKU_PERCENT_OFF.name -> PESkuRequest::class.java.name
                PromotionType.CROSS_SKU_AMOUNT_OFF.name -> PESkuRequest::class.java.name
                PromotionType.CROSS_SKU_FIXED_PRICE.name -> PESkuRequest::class.java.name

                PromotionType.SKU_ITEM_PERCENT_OFF.name -> PESkuRequest::class.java.name
                PromotionType.SKU_ITEM_AMOUNT_OFF.name -> PESkuRequest::class.java.name
                PromotionType.SKU_ITEM_FIXED_PRICE.name -> PESkuRequest::class.java.name
                else ->  PERequest::class.java.name
            }

            rules.add(rule)
        }
        eventLogger.log(PromotionEventType.PROMO_ENGINE_DATACOLLECTION,"getCurrentPRRules",rules )
        return rules
    }

    private fun sku1(): PERule {
        val rule = PERule()

        val condition1 = PromotionCondition( "price", "500.0", Operator.GREATER_THAN)
        val condition2 = PromotionCondition( "skuid", "SKU1,SKU2", Operator.IN)
//        val condition3 = PromotionCondition( "attr.color", "blue", Operator.EQUAL_TO)

        val action = PEAction(
            PEDiscount( promotionid = "PROMID1" ,discount = 15.0  )
        )

        rule.conditions = mutableListOf(condition1, condition2)
        rule.action = action
        rule.requestClassName = PESkuRequest::class.java.name
        return rule
    }
    private fun sku2(): PERule {
        val rule = PERule()


        val action = PEAction(
            PEDiscount( promotionid = "PROMID2" ,discount = 15.0  )
        )

        val condition1 = PromotionCondition( "price", "100.0", Operator.GREATER_THAN)
        val condition2 = PromotionCondition( "skuid", "SKU1", Operator.IN)
        val condition3 = PromotionCondition(  "price", "500.0", Operator.LESS_THAN)
        rule.conditions = mutableListOf(condition1, condition2,condition3)
        rule.action = action
        rule.requestClassName = PESkuRequest::class.java.name
        return rule
    }
    private fun orderRule1(): PERule {
        val rule = PERule()

        val condition1 = PromotionCondition( "totalskuprice", "250.0", Operator.GREATER_THAN)


        val action = PEAction(
            PEDiscount( promotionid = "PROMID3" ,discount = 15.0  )
        )

        rule.conditions = mutableListOf(condition1)
        rule.action = action
        rule.requestClassName = PEOrderRequest::class.java.name
        return rule
    }
}