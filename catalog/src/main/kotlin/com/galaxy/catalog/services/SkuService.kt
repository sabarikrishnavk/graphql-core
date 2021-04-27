
package com.galaxy.catalog.services

import com.galaxy.catalog.codegen.types.Sku
import org.springframework.stereotype.Service

interface SkuService {
    fun skus(): List<com.galaxy.catalog.codegen.types.Sku>
}

@Service
class BasicSkuService : SkuService {
    override fun skus(): List<com.galaxy.catalog.codegen.types.Sku> {
        return listOf(
//            com.galaxy.catalog.codegen.types.Sku(id = 1, title = "Stranger Things", releaseYear = 2016),
//            com.galaxy.catalog.codegen.types.Show(id = 2, title = "Ozark", releaseYear = 2017),
//            com.galaxy.catalog.codegen.types.Show(id = 3, title = "The Crown", releaseYear = 2016),
//            com.galaxy.catalog.codegen.types.Show(id = 4, title = "Dead to Me", releaseYear = 2019),
//            com.galaxy.catalog.codegen.types.Show(id = 5, title = "Orange is the New Black", releaseYear = 2013)
        )
    }
}