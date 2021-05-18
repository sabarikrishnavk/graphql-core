
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
import org.kie.api.runtime.KieContainer
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
    lateinit var  eventLogger: EventLogger


    @Autowired
    lateinit var  promotionEngine: PromotionEngine


    @BeforeEach
    fun before()  {

        val highValueOrderWidgetsIncRule = PERule()
        val highValueOrderCondition = PECondition()
        highValueOrderCondition.field = "price"
        highValueOrderCondition.operator = PECondition.Operator.GREATER_THAN
        highValueOrderCondition.value = 5000.0
//        val widgetsIncCustomerCondition = PECondition()
//        widgetsIncCustomerCondition.field = "skuid"
//        widgetsIncCustomerCondition.operator = PECondition.Operator.EQUAL_TO
//        widgetsIncCustomerCondition.value = "SKU1"


        val widgetsIncCustomerCondition = PECondition()
        widgetsIncCustomerCondition.field = "skuid"
        widgetsIncCustomerCondition.operator = PECondition.Operator.IN
        widgetsIncCustomerCondition.value = "SKU1,SKU2"


        val sizeCondition = PECondition()
        sizeCondition.field = "attr.color"
        sizeCondition.operator = PECondition.Operator.EQUAL_TO
        sizeCondition.value = "blue"

        val action = PEAction(
            PEDiscount(promotionid = "PROMO1",promotiondescription = "First promo",
                discount = 10.0 , "","")
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition, widgetsIncCustomerCondition,sizeCondition)
        highValueOrderWidgetsIncRule.action= action
        highValueOrderWidgetsIncRule.requestClassName= PESkuRequest::class.java.name


        `when`(promotionService.activeSKURules()).thenAnswer {

            listOf(highValueOrderWidgetsIncRule)
        }
        droolsConfiguration = DroolsConfiguration(promotionService,eventLogger )
        promotionEngine= PromotionEngine(droolsConfiguration.getKieContainer());
    }

    @Test
    fun evalulateSkuRequest() {

//        println("Rules drl: "+drl)

        val skuRequest1 = PESkuRequest("SKU1",2.0,"STH")
        val attr = HashMap<String,String>();
        attr.put("color","blue");
        attr.put("size","M")
        skuRequest1.attr= attr
        skuRequest1.price = 10500.0

        val result1 = promotionEngine.evaluate(skuRequest1);
        assertThat(result1.discounts[0].discount).isEqualTo(10.0)

        val skuRequest2 = PESkuRequest("SKU1",2.0,"STH")
        skuRequest2.price = 1500.0

        val result2 = promotionEngine.evaluate(skuRequest2);
        assertThat(result2.discounts.size).isEqualTo(0)

        val skuRequest3 = PESkuRequest("SKU2",2.0,"STH")
        skuRequest3.price = 5050.0
        skuRequest3.attr= attr

        val result3 = promotionEngine.evaluate(skuRequest3)
        assertThat(result3.discounts[0].discount).isEqualTo(10.0)
//        assertThat(result3.discounts.size).isEqualTo(0)

    }

}