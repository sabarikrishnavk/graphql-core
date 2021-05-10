
package com.galaxy.price.controller

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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.OffsetDateTime

@SpringBootTest(classes = [PriceController::class, DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
class PriceControllerTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var priceService: PriceService


    @BeforeEach
    fun before() {
        `when`(priceService.price()).thenAnswer {

            listOf(
                Price(skuid = "SKU1", location = "WH1", price = 25 , listprice = 25,priceId = "SKU1WH1V1"),
                Price(skuid = "SKU1", location = "WH2", price = 20 , listprice = 25,priceId = "SKU1WH2V1"),
                Price(skuid = "SKU2", location = "WH1", price = 50 , listprice = 50,priceId = "SKU2WH1V1"),
                Price(skuid = "SKU2", location = "WH2", price = 55 , listprice = 50,priceId = "SKU2WH2V1")
            )
        }

    }

    @Test
    fun price() {
        val price: List<Integer> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
              priceSkuLocation(skuid:"SKU1",location:"WH1"){
                skuid
                location
                price
              }
            }
        """.trimIndent(), "data.priceSkuLocation[*].price"
        )

        assertThat(price[0]).isEqualTo(25)
    }

    @Test
    fun showsWithException() {
        `when`(priceService.price()).thenThrow(RuntimeException("nothing to see here"))

        val result = dgsQueryExecutor.execute(
            """
            {
              priceSkuLocation(skuid:"SKU1",location:"WH1"){
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
    fun inventoryWithQueryApi() {
        val graphQLQueryRequest =
            GraphQLQueryRequest(
                com.galaxy.price.codegen.client.PriceSkuLocationGraphQLQuery.Builder()
                    .skuid("SKU1")
                    .location("WH1")
                    .build(),
                com.galaxy.price.codegen.client.PriceSkuLocationProjectionRoot().price().location().skuid()
            )
        val price = dgsQueryExecutor.executeAndExtractJsonPath<List<Integer>>(
            graphQLQueryRequest.serialize(),
            "data.priceSkuLocation[*].price"
        )
        assertThat(price[0]).isEqualTo(25)
    }
}