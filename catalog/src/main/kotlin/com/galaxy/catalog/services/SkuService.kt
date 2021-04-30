
package com.galaxy.catalog.services

import com.galaxy.catalog.codegen.types.Sku
import org.springframework.stereotype.Service
import java.util.*

interface SkuService {
    fun skus(): List<com.galaxy.catalog.codegen.types.Sku>
}

@Service
class BasicSkuService : SkuService {
    override fun skus(): List<com.galaxy.catalog.codegen.types.Sku> {
        return listOf(
            Sku(id = UUID.randomUUID().toString(), name = "Stranger Things", price = 123),
            Sku(id = UUID.randomUUID().toString(), name = " Things", price = 456),
            Sku(id = UUID.randomUUID().toString(), name = "Stranger ", price = 789)
        )
    }
}