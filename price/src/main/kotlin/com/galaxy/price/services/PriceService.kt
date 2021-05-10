
package com.galaxy.price.services

import com.galaxy.price.codegen.types.Price
import org.springframework.stereotype.Service

@Service
class PriceService {
    fun price(): List<Price> {
        return listOf(
            Price(skuid = "SKU1", location = "WH1", price = 25 , listprice = 25,priceId = "SKU1WH1V1"),
            Price(skuid = "SKU1", location = "WH2", price = 20 , listprice = 25,priceId = "SKU1WH2V1"),
            Price(skuid = "SKU2", location = "WH1", price = 50 , listprice = 50,priceId = "SKU2WH1V1"),
            Price(skuid = "SKU2", location = "WH2", price = 55 , listprice = 50,priceId = "SKU2WH2V1")
        )
    }
}