package com.galaxy.promotion.engine

import com.galaxy.promotion.codegen.types.DiscountType
import com.galaxy.promotion.codegen.types.Discounts
import com.galaxy.promotion.codegen.types.ReturnCartItem
import org.kie.api.runtime.KieContainer
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class PromotionEngine(private val kieContainer: KieContainer?) {


    @Throws(Exception::class)
    fun evaluateSkuRequest(requestList: List<PESkuRequest> ): List<ReturnCartItem> {

        var results= mutableListOf<ReturnCartItem>()
        for (request in requestList) {
            val result = PEResult()

            val kieSession = kieContainer!!.newKieSession()
            kieSession.insert(request)
            kieSession.globals["result"] = result
            kieSession.fireAllRules()
            kieSession.dispose()
            var discounts = mutableListOf<Discounts?>()

            result.discounts.forEach {
                discounts.add(Discounts(request.location!! , "", it.discount, DiscountType.FIXED_AMOUNT ) )
            }

            var returnCartItem = ReturnCartItem(request.skuid, request.quantity, request.price , discounts)


            results.add(returnCartItem)
        }
        return results
    }

}


