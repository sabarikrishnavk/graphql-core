package com.galaxy.promotion.services

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.engine.*
import com.galaxy.promotion.util.PromotionEventType
import org.springframework.stereotype.Service
import java.util.*

@Service
class PromotionService( val eventLogger: EventLogger){

    fun activeSKURules():List<PERule>{

        val rule1 = orderItemRule1()
        val rule2 = orderItemRule2()
        val rule3 = orderRule1()
        eventLogger.log(PromotionEventType.PROMO_ENGINE_DATACOLLECTION,"getCurrentPRRules",rule1,rule2,rule3 )
        return listOf(rule1,rule2,rule3)
    }

    private fun orderItemRule1(): PERule {
        val highValueOrderWidgetsIncRule = PERule()
        val highValueOrderCondition = PECondition()
        highValueOrderCondition.field = "price"
        highValueOrderCondition.operator = PECondition.Operator.GREATER_THAN
        highValueOrderCondition.value = 100.0
        val widgetsIncCustomerCondition = PECondition()
        widgetsIncCustomerCondition.field = "skuid"
        widgetsIncCustomerCondition.operator = PECondition.Operator.EQUAL_TO
        widgetsIncCustomerCondition.value = "SKU1"

        val action = PEAction(
            PEDiscount(
                promotionid = "PROMO1", promotiondescription = "First promo",
                discount = 10.0, 0.0,"", ""
            )
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition, widgetsIncCustomerCondition)
        highValueOrderWidgetsIncRule.action = action
        highValueOrderWidgetsIncRule.requestClassName = PESkuRequest::class.java.name
        return highValueOrderWidgetsIncRule
    }
    private fun orderItemRule2(): PERule {
        val highValueOrderWidgetsIncRule = PERule()

        val highValueOrderCondition1 = PECondition()
        highValueOrderCondition1.field = "price"
        highValueOrderCondition1.operator = PECondition.Operator.GREATER_THAN
        highValueOrderCondition1.value = 50.0


        val highValueOrderCondition2 = PECondition()
        highValueOrderCondition2.field = "price"
        highValueOrderCondition2.operator = PECondition.Operator.LESS_THAN
        highValueOrderCondition2.value = 100.0

        val widgetsIncCustomerCondition = PECondition()
        widgetsIncCustomerCondition.field = "skuid"
        widgetsIncCustomerCondition.operator = PECondition.Operator.EQUAL_TO
        widgetsIncCustomerCondition.value = "SKU1"

        val action = PEAction(
            PEDiscount(
                promotionid = "PROMO2", promotiondescription = "Second promo",
                discount = 5.0, 0.0,"", ""
            )
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition1,highValueOrderCondition2, widgetsIncCustomerCondition)
        highValueOrderWidgetsIncRule.action = action
        highValueOrderWidgetsIncRule.requestClassName = PESkuRequest::class.java.name
        return highValueOrderWidgetsIncRule
    }
    private fun orderRule1(): PERule {
        val highValueOrderWidgetsIncRule = PERule()

        val highValueOrderCondition1 = PECondition()
        highValueOrderCondition1.field = "totalskuprice"
        highValueOrderCondition1.operator = PECondition.Operator.GREATER_THAN
        highValueOrderCondition1.value = 250.0


        val action = PEAction(
            PEDiscount(
                promotionid = "PROMO3", promotiondescription = "Third promo",
                discount = 15.0, 0.0, "",""
            )
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition1)
        highValueOrderWidgetsIncRule.action = action
        highValueOrderWidgetsIncRule.requestClassName = PEOrderRequest::class.java.name
        return highValueOrderWidgetsIncRule
    }
}