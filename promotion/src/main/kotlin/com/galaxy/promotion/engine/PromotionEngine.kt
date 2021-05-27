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
            var discounts = fireRules(request)

            var totaldiscount = 0.0
            discounts?.forEach {
                    discount -> totaldiscount = totaldiscount.plus(discount!!.discount)
            }
            var returnCartItem = ReturnCartItem(request.skuid, request.quantity,    request.quantity.times( request.price ).minus(totaldiscount) , request.price,totaldiscount, discounts)


            results.add(returnCartItem)
        }
        return results
    }

    fun evaluateOrderRequest(request: PEOrderRequest): MutableList<Discounts?> {
        return fireRules(request)
    }

    private fun fireRules(request: PERequest): MutableList<Discounts?> {
        var discounts = mutableListOf<Discounts?>()

        val result = PEResult()

        val kieSession = kieContainer!!.newKieSession()
        kieSession.insert(request)
        kieSession.globals["result"] = result
        kieSession.fireAllRules()
        kieSession.dispose()

        result.discounts.forEach {
            discounts.add(Discounts(request.location!! , it.promotionid , it.discount, DiscountType.FIXED_AMOUNT ) )
        }
        return discounts
    }


    fun calculateOrderTotal(returnCartItems: List<ReturnCartItem>): PEOrderRequest {
        var totalskuprice: Double =0.0
        var totalshipping: Double =0.0
        var totaldiscount: Double=0.0
        var total: Double=0.0
        returnCartItems.forEach {
            totalskuprice = totalskuprice.plus(it.price)
            it.discounts?.forEach {
                    discounts -> totaldiscount = totaldiscount.plus(discounts!!.discount)
            }
        }
        total = totalskuprice.plus(totalshipping).minus(totaldiscount)
        return PEOrderRequest(totalskuprice,totalshipping,totaldiscount, total)
    }

}


