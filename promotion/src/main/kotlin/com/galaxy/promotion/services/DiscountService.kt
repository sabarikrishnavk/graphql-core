
package com.galaxy.promotion.services

import com.galaxy.promotion.codegen.types.*
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun discounts(skuids : List<String>,location:String) :List<Discounts>{

        //Get the sku information from catalog service and evaluate promotion skurules
        return dummyDiscounts()
    }
    fun discounts(skuids : List<String>) :List<Discounts>{
        return dummyDiscounts()
    }

    fun getPromotion(promotionid: String): Promotion {

        return dummyPromotions().filter {  it.promotionid == promotionid }.get(0)
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



    fun dummyPromotions() :List<Promotion>{
        return listOf(
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
                    admindesc = null
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
                admindesc = null
            ),
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
                admindesc = null
            )
        )
    }

}