package com.galaxy.promotion.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.Cart
import com.galaxy.promotion.codegen.types.ReturnCart
import com.galaxy.promotion.engine.PEOrder
import com.galaxy.promotion.engine.PEResult
import com.galaxy.promotion.engine.PESkuRequest
import com.galaxy.promotion.engine.PromotionEngine
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation

@DgsComponent
class PromotionEngineController(private val promotionEngine: PromotionEngine , val eventLogger: EventLogger){

    @DgsMutation
    fun evaluatePromotion( cart: Cart): ReturnCart? {

        //val peOrder = PEOrder(cart.cartid,cart.totalprice,cart.discount)

        val skuRequest = PESkuRequest("SKU1",2.0,"STH")
        skuRequest.price = cart.totalprice
        skuRequest.customer = "Widgets Inc."

        val peResult: PEResult= promotionEngine.evaluate(skuRequest);

        val discounts = peResult.discounts.get(0).discount;
        val result = ReturnCart(cart.cartid,null,cart.totalprice,discounts)
        return result
    }
}
