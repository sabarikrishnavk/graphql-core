
package com.galaxy.inventory.services

import com.galaxy.inventory.codegen.types.Inventory
import org.springframework.stereotype.Service

@Service
class InventoryService {
    fun inventory(): List<com.galaxy.inventory.codegen.types.Inventory> {
        return listOf(
            Inventory(skuid = "SKU1", location = "WH1", totalQty = 25),
            Inventory(skuid = "SKU1", location = "WH2", totalQty = 100),
            Inventory(skuid = "SKU2", location = "WH1", totalQty = 10),
            Inventory(skuid = "SKU2", location = "WH2", totalQty = 5)
        )
    }
}