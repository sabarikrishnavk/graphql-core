package com.galaxy.promotion.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.*
import com.galaxy.promotion.engine.PEOrder
import com.galaxy.promotion.engine.PEResult
import com.galaxy.promotion.engine.PESkuRequest
import com.galaxy.promotion.engine.PromotionEngine
import com.galaxy.promotion.services.CatalogService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation

@DgsComponent
class PromotionEngineController(private val promotionEngine: PromotionEngine ,
                                val catalogService: CatalogService,
                                val eventLogger: EventLogger){

    @DgsMutation
    fun evaluatePromotion( cart: Cart?): ReturnCart? {

        //val peOrder = PEOrder(cart.cartid,cart.totalprice,cart.discount)

        var skuids = cart?.items?.map { it?.skuid }

        catalogService.getSkuDetails(skuids);


        val skuRequest = PESkuRequest("SKU1",2.0,"STH")
        skuRequest.price = cart?.totalprice
        val attr = HashMap<String,String>();
        attr.put("color","blue");
        attr.put("size","M")
        skuRequest.attr= attr

        var request = mutableListOf<PESkuRequest>()
        request.add(skuRequest)
        val peResult= promotionEngine.evaluate(request);

        val discounts = peResult[0].discounts.get(0).discount;

        val result = ReturnCart(cart!!.cartid, cart.totalprice, listOf<ReturnCartItem>(), listOf<ReturnCartPayment>(),
            listOf())

        return result
    }
}
