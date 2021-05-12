package com.galaxy.promotion.controller

import com.galaxy.promotion.codegen.types.Cart
import com.galaxy.promotion.codegen.types.ReturnCart
import com.galaxy.promotion.engine.PECart
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import org.kie.api.runtime.KieContainer

@DgsComponent
class PromotionEngineController(private val kieContainer: KieContainer?)  {


    @DgsMutation
    fun evaluatePromotion( cart: Cart): ReturnCart? {
        val kieSession = kieContainer!!.newKieSession()

        val promoEngineCart = PECart(cart.cartid,cart.paymenttype,cart.totalprice,cart.discount)
        kieSession.insert(promoEngineCart)
        kieSession.fireAllRules()
        kieSession.dispose()
        val result = ReturnCart(promoEngineCart.cartid,promoEngineCart.paymenttype,promoEngineCart.totalprice,promoEngineCart.discount)
        return result
    }
}
