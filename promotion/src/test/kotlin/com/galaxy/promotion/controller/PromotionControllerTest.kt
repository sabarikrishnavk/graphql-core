
package com.galaxy.promotion.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.foundation.scalars.DateTimeScalarRegistration
import com.galaxy.promotion.codegen.types.Discounts
import com.galaxy.promotion.services.DiscountService
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [PromotionController::class,EventLogger::class,DiscountService ::class, DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
class PromotionControllerTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var discountService: DiscountService



    @BeforeEach
    fun before() {
        `when`(discountService.discounts(Mockito.anyList(), Mockito.anyString())).thenAnswer {

            DiscountService().dummyDiscounts();
        }

    }

    @Test
    fun discounts() {
        val price: List<Double> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
              discountSkusLocation(skuids:["SKU1"],location:"WH1"){
                skuid
                location
                discount  
              } 
            }
        """.trimIndent(), "data.discountSkusLocation[*].discount"
        )

        assertThat(price[0]).isEqualTo(15.0)
    }

    @Test
    fun discountsWithException() {
        `when`(discountService.discounts(Mockito.anyList(), Mockito.anyString())).thenThrow(RuntimeException("nothing to see here"))

        val result = dgsQueryExecutor.execute(
            """
            {
              discountSkusLocation(skuids:["SKU1"],location:"STR1"){
                skuid
                location
                discount 
              } 
            }
        """.trimIndent()
        )

        assertThat(result.errors).isNotEmpty
        assertThat(result.errors[0].message).isEqualTo("java.lang.RuntimeException: nothing to see here")
    }

    @Test
    fun discountsWithQueryApi() {
        val graphQLQueryRequest =
            GraphQLQueryRequest(
                com.galaxy.promotion.codegen.client.DiscountSkusLocationGraphQLQuery.Builder()
                    .skuids(listOf("SKU1"))
                    .location("STR1")
                    .build(),
                com.galaxy.promotion.codegen.client.DiscountSkusLocationProjectionRoot().discount()
            )
        val amountoff = dgsQueryExecutor.executeAndExtractJsonPath<List<Double>>(
            graphQLQueryRequest.serialize(),
            "data.discountSkusLocation[*].discount"
        )
        assertThat(amountoff[0]).isEqualTo(15.0)
    }
}