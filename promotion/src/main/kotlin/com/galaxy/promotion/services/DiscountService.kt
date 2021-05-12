
package com.galaxy.promotion.services

import com.galaxy.promotion.codegen.types.DiscountDetail
import com.galaxy.promotion.codegen.types.DiscountType
import com.galaxy.promotion.codegen.types.Discounts
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun discounts(): List<Discounts> {
        return listOf(
            Discounts(priceid = "SKU1WH1V1" , skuid = "SKU1", location = "WH1", price = 25 , discountdtl = listOf(DiscountDetail(type=DiscountType.AMOUNT_OFF,amountoff = 5))),
            Discounts(priceid = "SKU1WH2V1" , skuid = "SKU1", location = "WH2", price = 25 , discountdtl = listOf(DiscountDetail(type=DiscountType.PERCENTAGE_OFF,percentage = 25))),
            Discounts(priceid = "SKU2WH1V1" , skuid = "SKU2", location = "WH1", price = 25 , discountdtl = listOf(DiscountDetail(type=DiscountType.AMOUNT_OFF,amountoff = 5))),
            Discounts(priceid = "SKU2WH2V1" , skuid = "SKU2", location = "WH2", price = 25 , discountdtl = listOf(DiscountDetail(type=DiscountType.FIXED_AMOUNT,amountoff = 20))),
        )
    }
}