
package com.galaxy.price.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.foundation.scalars.DateTimeScalarRegistration
import com.galaxy.price.codegen.types.Price
import com.galaxy.price.services.PriceService
import com.jayway.jsonpath.TypeRef
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import graphql.ExecutionResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.OffsetDateTime

@SpringBootTest(classes = [PriceController::class, EventLogger::class, DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
class PriceControllerTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var priceService: PriceService


    @BeforeEach
    fun before() {
        `when`(priceService.price(Mockito.anyList(), Mockito.anyString())).thenAnswer {

            listOf(
                Price(skuid = "SKU1", location = "STR1", price = 25.0 , wasprice = 25.0, listprice = 25.0,priceid = "SKU1WH1V1"),
                Price(skuid = "SKU1", location = "STR2", price = 20.0 , wasprice = 25.0, listprice = 25.0,priceid = "SKU1WH2V1"),
                Price(skuid = "SKU2", location = "STR1", price = 50.0 , wasprice = 25.0, listprice = 50.0,priceid = "SKU2WH1V1"),
                Price(skuid = "SKU2", location = "STR2", price = 55.5 , wasprice = 25.0, listprice = 50.5,priceid = "SKU2WH2V1")
            )
        }

    }

    @Test
    fun price() {
        val price: List<Double> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
              priceSkusLocation(skuids:["SKU1"],location:"STR1"){
                skuid
                location
                price
              }
            }
        """.trimIndent(), "data.priceSkusLocation[*].price"
        )

        assertThat(price[0]).isEqualTo(25.0)
    }

    @Test
    fun priceWithException() {
        `when`(priceService.price(Mockito.anyList(), Mockito.anyString())).thenThrow(RuntimeException("nothing to see here"))

        val result = dgsQueryExecutor.execute(
            """
            {
              priceSkusLocation(skuids:["SKU1"],location:"STR1"){
                skuid
                location
                price
              }
            }
        """.trimIndent()
        )

        assertThat(result.errors).isNotEmpty
        assertThat(result.errors[0].message).isEqualTo("java.lang.RuntimeException: nothing to see here")
    }

    @Test
    fun priceWithQueryApi() {
        val graphQLQueryRequest =
            GraphQLQueryRequest(
                com.galaxy.price.codegen.client.PriceSkusLocationGraphQLQuery.Builder()
                    .skuids(listOf("SKU1"))
                    .location("STR1")
                    .build(),
                com.galaxy.price.codegen.client.PriceSkusLocationProjectionRoot().price().location().skuid()
            )
        val price = dgsQueryExecutor.executeAndExtractJsonPath<List<Double>>(
            graphQLQueryRequest.serialize(),
            "data.priceSkusLocation[*].price"
        )
        assertThat(price[0]).isEqualTo(25.0)
    }
}