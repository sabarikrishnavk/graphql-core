
package com.galaxy.catalog.services

import org.springframework.stereotype.Service
import com.galaxy.catalog.codegen.types.Sku

@Service
class SkuService {
     fun skus(): List<Sku> {
        return listOf(
            Sku(skuid = "SKU1", name = "Stranger Things", price = 123),
            Sku(skuid = "SKU2", name = " Things", price = 456),
            Sku(skuid = "SKU3", name = "Stranger ", price = 789)
        )
    }
}