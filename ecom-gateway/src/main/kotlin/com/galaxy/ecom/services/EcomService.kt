
package com.galaxy.catalog.services

import com.galaxy.catalog.codegen.types.Sku
import org.springframework.stereotype.Service
import java.util.*

@Service
class SkuService {
     fun skus(): List<com.galaxy.catalog.codegen.types.Sku> {
        return listOf(
            Sku(id = "SKU1", name = "Stranger Things", price = 123),
            Sku(id = "SKU2", name = " Things", price = 456),
            Sku(id = "SKU3", name = "Stranger ", price = 789)
        )
    }
}