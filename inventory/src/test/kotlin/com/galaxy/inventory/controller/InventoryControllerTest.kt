
package com.galaxy.inventory.controller

import com.galaxy.foundation.scalars.DateTimeScalarRegistration
import com.galaxy.inventory.codegen.types.Inventory
import com.galaxy.inventory.services.InventoryService
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

@SpringBootTest(classes = [InventoryController::class, DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
class InventoryControllerTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var inventoryService: InventoryService


    @BeforeEach
    fun before() {
        `when`(inventoryService.inventory()).thenAnswer {

            listOf(
                Inventory(skuid = "SKU1", location = "WH1", totalQty = 25),
                Inventory(skuid = "SKU2", location = "WH1", totalQty = 10),
                Inventory(skuid = "SKU2", location = "WH2", totalQty = 5)
            )
        }

    }

    @Test
    fun inventory() {
        val quantity: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
              inventory(skuid:"SKU",location:"WH1"){
               skuid
               location
               totalQty
              }
            }
        """.trimIndent(), "data.inventory[*].totalQty"
        )

        assertThat(quantity).contains("25")
    }

    @Test
    fun showsWithException() {
        `when`(inventoryService.inventory()).thenThrow(RuntimeException("nothing to see here"))

        val result = dgsQueryExecutor.execute(
            """
            {
              inventory(skuid:"SKU",location:"WH1"){
               skuid
               location
               totalQty
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
                com.galaxy.inventory.codegen.client.InventoryGraphQLQuery.Builder()
                    .skuid("SKU1")
                    .location("WH1")
                    .build(),
                com.galaxy.inventory.codegen.client.InventoryProjectionRoot().totalQty().location().skuid()
            )
        val quantity = dgsQueryExecutor.executeAndExtractJsonPath<List<Integer>>(
            graphQLQueryRequest.serialize(),
            "data.inventory[*].totalQty"
        )
        assertThat(quantity[0]).isEqualTo(25)
    }
}