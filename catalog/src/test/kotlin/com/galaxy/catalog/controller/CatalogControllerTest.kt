
package com.galaxy.catalog.controller

import com.galaxy.catalog.codegen.types.Sku
import com.galaxy.catalog.services.SkuService
import com.galaxy.foundation.scalars.DateTimeScalarRegistration
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

@SpringBootTest(classes = [CatalogController::class, DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
class CatalogControllerTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var skuService: SkuService


    @BeforeEach
    fun before() {
        `when`(skuService.skus()).thenAnswer {

             listOf(
                Sku(skuid = "SKU1", name = "Stranger Things", price = 123),
                Sku(skuid = "SKU2", name = " Things", price = 456),
                Sku(skuid = "SKU3", name = "Stranger ", price = 789)
            )
         }

    }

    @Test
    fun skus() {
        val names: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
                skus (skuid:"SKU1") {
                    name
                    price
                }
            }
        """.trimIndent(), "data.skus[*].name"
        )

        assertThat(names).contains("Stranger Things")
    }

    @Test
    fun showsWithException() {
        `when`(skuService.skus()).thenThrow(RuntimeException("nothing to see here"))

        val result = dgsQueryExecutor.execute(
            """
             {
                skus (skuid:"SKU1") {
                    name
                    price
                }
            }
        """.trimIndent()
        )

        assertThat(result.errors).isNotEmpty
        assertThat(result.errors[0].message).isEqualTo("java.lang.RuntimeException: nothing to see here")
    }

    @Test
    fun skusWithQueryApi() {
        val graphQLQueryRequest =
            GraphQLQueryRequest(
                com.galaxy.catalog.codegen.client.SkusGraphQLQuery.Builder().build(),
                com.galaxy.catalog.codegen.client.SkusProjectionRoot().name()
            )
        val titles = dgsQueryExecutor.executeAndExtractJsonPath<List<String>>(
            graphQLQueryRequest.serialize(),
            "data.skus[*].name"
        )
        assertThat(titles[0]).contains("Stranger Things")
    }
}