
package com.galaxy.catalog.services

import com.galaxy.catalog.codegen.types.Attributes
import org.springframework.stereotype.Service
import com.galaxy.catalog.codegen.types.Sku
import com.galaxy.catalog.util.CatalogEvents
import com.galaxy.foundation.logger.EventLogger

@Service
class SkuService (val eventLogger: EventLogger){
     fun skus(skuids: List<String?>): List<Sku> {

         eventLogger.log(CatalogEvents.CATALOG_FIND,"finding skus ",skuids);

         var attributes1 = listOf(
             Attributes("ATTR1","color","blue",true,true,true),
             Attributes("ATTR1","color","red",true,true,true),
             Attributes("ATTR1","color","green",true,true,true),

             Attributes("ATTR2","size","M",true,true,true),
             Attributes("ATTR2","size","S",true,true,true),
             Attributes("ATTR2","size","L",true,true,true),
         )

         var attributes2 = listOf(
             Attributes("ATTR3","origin","US",true,false,false),
             Attributes("ATTR4","brand","adida",true,true,false)

         )

        return listOf(
            Sku(skuid = "SKU1", name = "Stranger Things", price = 123, attributes = listOf(attributes1.get(0),attributes1.get(3), attributes2.get(0),attributes2.get(1) ) ),
            Sku(skuid = "SKU2", name = " Things", price = 456, attributes = listOf(attributes1.get(1),attributes1.get(4), attributes2.get(0),attributes2.get(1) ) ),
            Sku(skuid = "SKU3", name = "Stranger ", price = 789, attributes = listOf(attributes1.get(2),attributes1.get(5), attributes2.get(0),attributes2.get(1) ) )
        )
    }
}