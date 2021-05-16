
package com.galaxy.promotion.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.foundation.scalars.DateTimeScalarRegistration
import com.galaxy.promotion.codegen.types.DiscountDetail
import com.galaxy.promotion.codegen.types.DiscountType
import com.galaxy.promotion.codegen.types.Discounts
import com.galaxy.promotion.services.DiscountService
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

@SpringBootTest(classes = [PromotionController::class,EventLogger::class,  DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
class PromotionControllerTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var discountService: DiscountService


    @BeforeEach
    fun before() {
        `when`(discountService.discounts()).thenAnswer {

             listOf(
                Discounts(priceid = "SKU1WH1V1" , skuid = "SKU1", location = "WH1", price =
                25.0 , discountdtl = listOf(
                    DiscountDetail(type= DiscountType.AMOUNT_OFF,amountoff = 5.0)
                )),
                Discounts(priceid = "SKU1WH2V1" , skuid = "SKU1", location = "WH2", price = 25.0 , discountdtl = listOf(
                    DiscountDetail(type= DiscountType.PERCENTAGE_OFF,percentage = 25.0)
                )),
                Discounts(priceid = "SKU2WH1V1" , skuid = "SKU2", location = "WH1", price = 25.0 , discountdtl = listOf(
                    DiscountDetail(type= DiscountType.AMOUNT_OFF,amountoff = 5.0)
                )),
                Discounts(priceid = "SKU2WH2V1" , skuid = "SKU2", location = "WH2", price = 25.0 , discountdtl = listOf(
                    DiscountDetail(type= DiscountType.FIXED_AMOUNT,amountoff = 20.0)
                )),
            )
        }

    }

    @Test
    fun discounts() {
        val price: List<Integer> = dgsQueryExecutor.executeAndExtractJsonPath(
            """{
              discountSkuLocation(skuid:"SKU1",location:"WH1"){
                skuid
                location
                price
                discountdtl{
                    type
                    percentage
                    fixed
                    amountoff
                }
              }
              discountBySku(skuid: "SKU1"){
                skuid
                location
                price
                discountdtl{
                    type
                    percentage
                    fixed
                    amountoff
                }
              }
            }
        """.trimIndent(), "data.discountSkuLocation[*].price"
        )

        assertThat(price[0]).isEqualTo(25.0)
    }

    @Test
    fun showsWithException() {
        `when`(discountService.discounts()).thenThrow(RuntimeException("nothing to see here"))

        val result = dgsQueryExecutor.execute(
            """
            {
              discountSkuLocation(skuid:"SKU1",location:"WH1"){
                skuid
                location
                price
                discountdtl{
                    type
                    percentage
                    fixed
                    amountoff
                }
              }
              discountBySku(skuid: "SKU1"){
                skuid
                location
                price
                discountdtl{
                    type
                    percentage
                    fixed
                    amountoff
                }
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
                com.galaxy.promotion.codegen.client.DiscountSkuLocationGraphQLQuery.Builder()
                    .skuid("SKU1")
                    .location("WH1")
                    .build(),
                com.galaxy.promotion.codegen.client.DiscountSkuLocationProjectionRoot().discountdtl().amountoff()
            )
        val amountoff = dgsQueryExecutor.executeAndExtractJsonPath<List<Integer>>(
            graphQLQueryRequest.serialize(),
            "data.discountSkuLocation[*].discountdtl[*].amountoff"
        )
        assertThat(amountoff[0]).isEqualTo(5.0)
    }
}