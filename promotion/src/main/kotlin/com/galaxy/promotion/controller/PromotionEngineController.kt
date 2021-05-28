package com.galaxy.promotion.controller

import com.galaxy.foundation.context.CustomContext
import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.*
import com.galaxy.promotion.engine.PEOrderRequest
import com.galaxy.promotion.engine.PESkuRequest
import com.galaxy.promotion.engine.PromotionEngine
import com.galaxy.promotion.services.CatalogService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.context.DgsContext
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize


@DgsComponent
class PromotionEngineController(private val promotionEngine: PromotionEngine ,
                                val catalogService: CatalogService,
                                val eventLogger: EventLogger){

    @DgsMutation
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
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

        var skuRequests = mutableListOf<PESkuRequest>()
        cart!!.items!!.forEach {
            val skuRequest = PESkuRequest(it!!.skuid,it!!.quantity!!,it.shipmode!!)
            skuRequest.price = priceMap.get(it!!.skuid)!!
            skuRequest.attr = skuMap.get(it!!.skuid)
            skuRequest.location= context.location
            skuRequests.add(skuRequest)
        }

        val returnCartItems = promotionEngine.evaluateSkuRequest(skuRequests)

        var orderRequest = promotionEngine.calculateOrderTotal(returnCartItems)

        orderRequest.price = orderRequest.total!!
        orderRequest.location= context.location

        val returnOrderDiscounts = promotionEngine.evaluateOrderRequest(orderRequest)

        var totaldiscount = 0.0
        returnOrderDiscounts?.forEach {
                discounts -> totaldiscount = totaldiscount.plus(discounts!!.discount)
        }
        orderRequest.totaldiscount = totaldiscount
        orderRequest.total =orderRequest.totalskuprice.plus(orderRequest.totalshipping).minus(totaldiscount)

        val result = ReturnCart(cartid = cart!!.cartid,
                                totalskuprice = orderRequest.totalskuprice,
                                totaldiscount =orderRequest.totaldiscount,
                                totalshipping =  orderRequest.totalshipping,
                                total = orderRequest.total,
                                items = returnCartItems,
                                listOf<ReturnCartPayment>(),
                                returnOrderDiscounts)

        return result
    }

}
