
package com.galaxy.promotion.services

import com.galaxy.price.codegen.types.Price
import org.springframework.stereotype.Service

@Service
class PromotionService {
    fun price(): List<Price> {
        return listOf(
            Price(skuid = "SKU1", location = "WH1", price = 25 , listprice = 25),
            Price(skuid = "SKU1", location = "WH2", price = 20 , listprice = 25),
            Price(skuid = "SKU2", location = "WH1", price = 50 , listprice = 50),
            Price(skuid = "SKU2", location = "WH2", price = 55 , listprice = 50)
        )
    }
}