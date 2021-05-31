
package com.galaxy.promotion.services

import com.galaxy.promotion.codegen.types.DiscountType
import com.galaxy.promotion.codegen.types.Discounts
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun discounts(skuids : List<String>,location:String) :List<Discounts>{
        return discounts()
    }
    fun discounts(skuids : List<String>) :List<Discounts>{
        return discounts()
    }
    fun discounts() :List<Discounts>{
        return listOf(
            Discounts(  promotionid =  "PROMOID11" ,skuid = "SKU1", location = "STR1", discount =  25.0 , discounttype = DiscountType.AMOUNT_OFF),
            Discounts(  promotionid =  "PROMOID12" ,skuid = "SKU2", location = "STR1", discount =  25.0 , discounttype = DiscountType.AMOUNT_OFF),
            Discounts(  promotionid =  "PROMOID21" ,skuid = "SKU1", location = "STR2", discount =  25.0 , discounttype = DiscountType.AMOUNT_OFF),
            Discounts(  promotionid =  "PROMOID22" ,skuid = "SKU2", location = "STR2", discount =  25.0 , discounttype = DiscountType.AMOUNT_OFF)
        )
    }
}