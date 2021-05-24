package com.galaxy.promotion.controller

import com.galaxy.catalog.codegen.types.Sku
import com.galaxy.foundation.context.CustomContext
import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.*
import com.galaxy.promotion.engine.PESkuRequest
import com.galaxy.promotion.engine.PromotionEngine
import com.galaxy.promotion.services.CatalogService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.context.DgsContext
import graphql.schema.DataFetchingEnvironment


@DgsComponent
class PromotionEngineController(private val promotionEngine: PromotionEngine ,
                                val catalogService: CatalogService,
                                val eventLogger: EventLogger){

    @DgsMutation
    fun evaluatePromotion( cart: Cart?, dfe: DataFetchingEnvironment?): ReturnCart? {

         val context= DgsContext.getCustomContext<CustomContext>(dfe!!);
        var skuids = cart?.items?.map { it?.skuid }

        val skus = catalogService.getSkuDetails(skuids,context);

        val priceMap = mutableMapOf<String,Double>()
        val skuMap = mutableMapOf<String,Map<String,String>>()
        skus!!.forEach {
            skuMap.put(it!!.skuid,
                it!!.attributes!!.filter { it!!.promotionable!! }
                    .associateBy({ it!!.name }, { it!!.value })
            )
            priceMap.put(it!!.skuid,  it!!.price!!)
        }

        var request = mutableListOf<PESkuRequest>()
        cart!!.items!!.forEach {
            val skuRequest = PESkuRequest(it!!.skuid,it!!.quantity!!,it.shipmode!!)
            skuRequest.price = priceMap.get(it!!.skuid)
            skuRequest.attr = skuMap.get(it!!.skuid)
            skuRequest.location= cart.location
            request.add(skuRequest)
        }


        val returnCartItems = promotionEngine.evaluateSkuRequest(request);



        val result = ReturnCart(cart!!.cartid, cart.totalprice, returnCartItems, listOf<ReturnCartPayment>(),
            listOf())

        return result
    }
}
