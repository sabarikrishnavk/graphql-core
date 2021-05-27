
package com.galaxy.price.services

import com.galaxy.price.codegen.types.Price
import org.springframework.stereotype.Service

@Service
class PriceService {
    fun price(): List<Price> {
        return listOf(
            Price(skuid = "SKU1", location = "STR1", price = 25.0 , wasprice = 25.0, listprice = 25.0,priceid = "SKU1WH1V1"),
            Price(skuid = "SKU1", location = "STR2", price = 20.0 , wasprice = 25.0, listprice = 25.0,priceid = "SKU1WH2V1"),
            Price(skuid = "SKU2", location = "STR1", price = 50.0 , wasprice = 25.0, listprice = 50.0,priceid = "SKU2WH1V1"),
            Price(skuid = "SKU2", location = "STR2", price = 55.5 , wasprice = 25.0, listprice = 50.5,priceid = "SKU2WH2V1")
        )
    }
    fun price(skuids : List<String> ): List<Price>{
        return price()
    }
    fun price(skuids : List<String> , location : String): List<Price>{
        return price()
    }
    fun price(skuid : String): List<Price>{
        return price()
    }
}