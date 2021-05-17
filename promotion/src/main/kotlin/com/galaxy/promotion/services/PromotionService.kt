package com.galaxy.promotion.services

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.engine.*
import com.galaxy.promotion.util.PromotionEventType
import org.springframework.stereotype.Service
import java.util.*

@Service
class PromotionService( val eventLogger: EventLogger){

    fun activeSKURules():List<PERule>{

        val highValueOrderWidgetsIncRule1 = rule1()
        val highValueOrderWidgetsIncRule2 = rule2()



        eventLogger.log(PromotionEventType.PROMO_ENGINE_DATACOLLECTION,"getCurrentPRRules",highValueOrderWidgetsIncRule1,highValueOrderWidgetsIncRule2 )
        return listOf(highValueOrderWidgetsIncRule1,highValueOrderWidgetsIncRule2)
    }

    private fun rule1(): PERule {
        val highValueOrderWidgetsIncRule = PERule()
        val highValueOrderCondition = PECondition()
        highValueOrderCondition.field = "price"
        highValueOrderCondition.operator = PECondition.Operator.GREATER_THAN
        highValueOrderCondition.value = 5000.0
        val widgetsIncCustomerCondition = PECondition()
        widgetsIncCustomerCondition.field = "skuid"
        widgetsIncCustomerCondition.operator = PECondition.Operator.EQUAL_TO
        widgetsIncCustomerCondition.value = "SKU1"

        val action = PEAction(
            PEDiscount(
                promotionid = "PROMO1", promotiondescription = "First promo",
                discount = 10.0, "", ""
            )
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition, widgetsIncCustomerCondition)
        highValueOrderWidgetsIncRule.action = action
        highValueOrderWidgetsIncRule.requestClassName = PESkuRequest::class.java.name
        return highValueOrderWidgetsIncRule
    }
    private fun rule2(): PERule {
        val highValueOrderWidgetsIncRule = PERule()

        val highValueOrderCondition1 = PECondition()
        highValueOrderCondition1.field = "price"
        highValueOrderCondition1.operator = PECondition.Operator.GREATER_THAN
        highValueOrderCondition1.value = 1000.0


        val highValueOrderCondition2 = PECondition()
        highValueOrderCondition2.field = "price"
        highValueOrderCondition2.operator = PECondition.Operator.LESS_THAN
        highValueOrderCondition2.value = 5000.0

        val widgetsIncCustomerCondition = PECondition()
        widgetsIncCustomerCondition.field = "skuid"
        widgetsIncCustomerCondition.operator = PECondition.Operator.EQUAL_TO
        widgetsIncCustomerCondition.value = "SKU1"

        val action = PEAction(
            PEDiscount(
                promotionid = "PROMO2", promotiondescription = "Second promo",
                discount = 5.0, "", ""
            )
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition1,highValueOrderCondition2, widgetsIncCustomerCondition)
        highValueOrderWidgetsIncRule.action = action
        highValueOrderWidgetsIncRule.requestClassName = PESkuRequest::class.java.name
        return highValueOrderWidgetsIncRule
    }
}