
package com.galaxy.promotion.engine

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.Operator
import com.galaxy.promotion.codegen.types.PromotionCondition
import com.galaxy.promotion.config.DroolsConfiguration
import com.galaxy.promotion.engine.objects.*
import com.galaxy.promotion.services.DiscountService
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

@SpringBootTest(classes = [PromotionEngine::class, EventLogger::class,PromotionCache::class,
    DiscountService::class,DroolsConfiguration::class, PromotionService::class])
class PromotionEngineTest {

    @MockBean
    lateinit var promotionService: PromotionService

    @Autowired
    lateinit var  droolsConfiguration: DroolsConfiguration

    @Autowired
    lateinit var discountService: DiscountService

    @Autowired
    lateinit var  promotionCache: PromotionCache

    @Autowired
    lateinit var  eventLogger: EventLogger


    @Autowired
    lateinit var  promotionEngine: PromotionEngine


    @BeforeEach
    fun before()  {


        `when`(promotionService.activeRules()).thenAnswer {
            PromotionService(eventLogger,discountService).activeRules()
        }
        droolsConfiguration = DroolsConfiguration(promotionService,eventLogger )
        promotionEngine= PromotionEngine(droolsConfiguration.getKieContainer() , promotionCache);
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
        assertThat(result1!!.get(0)!!.discounts!!.get(0)!!.discount).isEqualTo(15.0)

        request = mutableListOf<PESkuRequest>()
        request.add(skuRequest2);

        val result2 = promotionEngine.evaluateSkuRequest(request);
       assertThat(result2!!.get(0)!!.discounts!!.size).isEqualTo(0)

        request = mutableListOf<PESkuRequest>()
        request.add(skuRequest3);

        val result3 = promotionEngine.evaluateSkuRequest(request)
        assertThat(result3!!.get(0)!!.discounts!!.get(0)!!.discount).isEqualTo(15.0)
//        assertThat(result3.discounts.size).isEqualTo(0)

    }

}