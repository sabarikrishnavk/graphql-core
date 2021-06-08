
package com.galaxy.promotion.services

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.*
import com.galaxy.promotion.engine.objects.*
import com.galaxy.promotion.util.PromotionEventType
import org.springframework.stereotype.Service

@Service
class DiscountService (val eventLogger: EventLogger){


    fun activeRules():MutableList<PERule>{

        val promotions = getActivePromotions()

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


    fun discounts(skuids : List<String>,location:String) :List<Discounts>{

        //Get the sku information from catalog service and evaluate promotion skurules
        return dummyDiscounts()
    }

    fun discounts(skuids : List<String>) :List<Discounts>{
        return dummyDiscounts()
    }

    fun getPromotion(promotionid: String): Promotion {

        return promotions.filter {  it.promotionid == promotionid }.get(0)
    }

    fun getActivePromotions():MutableList<Promotion>{
        return promotions;
    }

    fun dummyDiscounts() :List<Discounts>{
        return listOf(
            Discounts(  location = "STR1" ,
                        discount = getPromotion("PROMID1").discount,
                        skuid = "SKU1",
                        shipmode =null,
                        promotion = getPromotion("PROMID1")
            ),
            Discounts(  location = "STR1" ,
                discount = getPromotion("PROMID2").discount,
                skuid = "SKU2",
                shipmode =null,
                promotion = getPromotion("PROMID2")
            ),

            Discounts(  location = "STR1" ,
                discount = getPromotion("PROMID3").discount,
                skuid = null,
                shipmode =null,
                promotion = getPromotion("PROMID3")
            )
          )
    }

    fun savePromotions(promotion:Promotion): Promotion {
        promotions.add(promotion)
        return promotion;
    }

    fun dummyPromotions() :MutableList<Promotion> {
        return promotions;
    }

    var promotions = mutableListOf(
            Promotion(
                    promotionid = "PROMID1",
                    promotionType =  PromotionType.SKU_ITEM_AMOUNT_OFF,
                    status = PromotionStatus.ACTIVE ,
                    discount = 15.0,
                    stackable= false,
                    rank = 1,
                    affectedskus = null ,
                    startdate = "2021-06-01 : 00:00:00",
                    enddate = "2099-06-01 : 00:00:00" ,
                    cartdesc = null,
                    pdpdesc = null,
                    plpdesc = null,
                    admindesc = null,

                    conditions =mutableListOf(
                        PromotionCondition( "price", "100.0", Operator.GREATER_THAN),
                        PromotionCondition( "skuid", "SKU1", Operator.IN),
                        PromotionCondition(  "price", "500.0", Operator.LESS_THAN)
                    )
            ),
            Promotion(
                promotionid = "PROMID2",
                promotionType =  PromotionType.SKU_ITEM_AMOUNT_OFF,
                status = PromotionStatus.ACTIVE ,
                discount = 10.0,
                stackable= false,
                rank = 1,
                affectedskus = null ,
                startdate = "2021-06-01 : 00:00:00",
                enddate = "2099-06-01 : 00:00:00" ,
                cartdesc = null,
                pdpdesc = null,
                plpdesc = null,
                admindesc = null,
                conditions =mutableListOf(
                    PromotionCondition( "price", "500.0", Operator.GREATER_THAN),
                    PromotionCondition( "skuid", "SKU1", Operator.IN)
                )
            )
            ,
            Promotion(
                promotionid = "PROMID3",
                promotionType =  PromotionType.ORDER_AMOUNT_OFF,
                status = PromotionStatus.ACTIVE ,
                discount = 10.0,
                stackable= false,
                rank = 1,
                affectedskus = null ,
                startdate = "2021-06-01 : 00:00:00",
                enddate = "2099-06-01 : 00:00:00" ,
                cartdesc = null,
                pdpdesc = null,
                plpdesc = null,
                admindesc = null,
                conditions =mutableListOf(
                    PromotionCondition( "totalskuprice", "250.0", Operator.GREATER_THAN)
                )
            )
        )


}