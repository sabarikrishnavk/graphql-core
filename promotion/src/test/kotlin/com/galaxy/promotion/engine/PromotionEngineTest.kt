
package com.galaxy.promotion.engine

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.config.DroolsConfiguration
import com.galaxy.promotion.engine.objects.*
import com.galaxy.promotion.services.PromotionService
import com.google.gson.Gson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

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
        highValueOrderCondition.value = 100.0
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
                discount = 10.0 , 0.0,"","")
        )

        highValueOrderWidgetsIncRule.conditions = Arrays.asList(highValueOrderCondition, widgetsIncCustomerCondition,sizeCondition)
        highValueOrderWidgetsIncRule.action= action
        highValueOrderWidgetsIncRule.requestClassName= PESkuRequest::class.java.name

        val gson = Gson().toJson(highValueOrderWidgetsIncRule).replace("\"","'")
        println(gson);


        `when`(promotionService.activeSKURules()).thenAnswer {

            listOf(highValueOrderWidgetsIncRule)
        }
        droolsConfiguration = DroolsConfiguration(promotionService,eventLogger )
        promotionEngine= PromotionEngine(droolsConfiguration.getKieContainer());
    }

    @Test
    fun evaluateSkuRequest() {

//        println("Rules drl: "+drl)

        val skuRequest1 = PESkuRequest("SKU1",2.0,"STH")
        val attr = HashMap<String,String>();
        attr.put("color","blue");
        attr.put("size","M")
        skuRequest1.location="STR1"
        skuRequest1.attr= attr
        skuRequest1.price = 150.0

        val skuRequest2 = PESkuRequest("SKU2",2.0,"STH")
        skuRequest2.price = 200.0
        skuRequest2.location="STR1"
        val skuRequest3 = PESkuRequest("SKU1",2.0,"STH")
        skuRequest3.price = 150.0
        skuRequest3.location="STR1"
        skuRequest3.attr= attr

        var request = mutableListOf<PESkuRequest>()
        request.add(skuRequest1);

        val result1 = promotionEngine.evaluateSkuRequest(request);
        assertThat(result1!!.get(0)!!.discounts!!.get(0)!!.discount).isEqualTo(10.0)

        request = mutableListOf<PESkuRequest>()
        request.add(skuRequest2);

        val result2 = promotionEngine.evaluateSkuRequest(request);
       assertThat(result2!!.get(0)!!.discounts!!.size).isEqualTo(0)

        request = mutableListOf<PESkuRequest>()
        request.add(skuRequest3);

        val result3 = promotionEngine.evaluateSkuRequest(request)
        assertThat(result3!!.get(0)!!.discounts!!.get(0)!!.discount).isEqualTo(10.0)
//        assertThat(result3.discounts.size).isEqualTo(0)

    }

}