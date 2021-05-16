package com.galaxy.promotion.services

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.engine.*
import com.galaxy.promotion.util.PromotionEventType
import org.springframework.stereotype.Service
import java.util.*

@Service
class PromotionService( val eventLogger: EventLogger){

    fun getCurrentPRRules():List<PERule>{

        val highValueOrderWidgetsIncRule = PERule()
        val highValueOrderCondition = PECondition()
        highValueOrderCondition.field = "price"
        highValueOrderCondition.operator = PECondition.Operator.GREATER_THAN
        highValueOrderCondition.value = 5000.0
        val widgetsIncCustomerCondition = PECondition()
        widgetsIncCustomerCondition.field = "customer"
        widgetsIncCustomerCondition.operator = PECondition.Operator.EQUAL_TO
        widgetsIncCustomerCondition.value = "Widgets Inc."

        val action = PEAction(
            PEDiscount(promotionid = "PROMO1",promotiondescription = "First promo",
                discount = 10.0 , "","")
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition, widgetsIncCustomerCondition)
        highValueOrderWidgetsIncRule.action= action
        highValueOrderWidgetsIncRule.requestClassName= PESkuRequest::class.java.name


        eventLogger.log(PromotionEventType.PROMO_ENGINE_DATACOLLECTION,"getCurrentPRRules",highValueOrderWidgetsIncRule )
        return listOf(highValueOrderWidgetsIncRule)
    }
}