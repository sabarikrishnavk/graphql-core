
package com.galaxy.promotion.services

import com.galaxy.promotion.codegen.types.Discounts
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun discountBySkuLocation(skuid:String,location:String) :List<Discounts>{
        return listOf()
    }
    fun discountBySku(skuid:String) :List<Discounts>{
        return listOf()
    }
}