
package com.galaxy.promotion.engine

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.foundation.scalars.DateTimeScalarRegistration
import com.galaxy.promotion.codegen.types.DiscountDetail
import com.galaxy.promotion.codegen.types.DiscountType
import com.galaxy.promotion.codegen.types.Discounts
import com.galaxy.promotion.config.DroolsConfiguration
import com.galaxy.promotion.services.DiscountService
import com.galaxy.promotion.services.PromotionService
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest(classes = [PromotionEngine::class, EventLogger::class,DroolsConfiguration::class, PromotionService::class])
class PromotionEngineTest {

    @MockBean
    lateinit var promotionService: PromotionService

    @Autowired
    lateinit var  droolsConfiguration: DroolsConfiguration

    @Autowired
    lateinit var  promotionEngine: PromotionEngine


    @BeforeEach
    fun before()  {

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


        `when`(promotionService.getCurrentPRRules()).thenAnswer {

             listOf(highValueOrderWidgetsIncRule)
        }
    }

    @Test
    fun evalulateSkuRequest() {

        val drl = droolsConfiguration.applyRuleTemplate(promotionService.getCurrentPRRules());
        println("Rules drl: "+drl)

        val skuRequest = PESkuRequest("SKU1",2.0,"STH")
        skuRequest.price = 5001.0
        skuRequest.customer = "Widgets Inc."

        val result = promotionEngine.evaluate(skuRequest);

        println(result.discounts);

    }

    @Test
    fun showsWithException() {
    }

    @Test
    fun inventoryWithQueryApi() {
    }
}