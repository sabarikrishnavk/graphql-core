
package com.galaxy.mdm.controller

import com.galaxy.foundation.scalars.DateTimeScalarRegistration
import com.galaxy.mdm.codegen.types.Location
import com.galaxy.mdm.codegen.types.LocationAddress
import com.galaxy.mdm.codegen.types.OperationHours
import com.galaxy.mdm.controller.MdmController
import com.galaxy.mdm.services.MdmService
import com.jayway.jsonpath.TypeRef
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import graphql.ExecutionResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.OffsetDateTime
import kotlin.reflect.typeOf

@SpringBootTest(classes = [MdmController::class, DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
class MdmControllerTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var mdmService: MdmService


    @BeforeEach
    fun before() {

        var address = listOf(
            LocationAddress("Universal studio","hollywood","LA street","Los Angeles","CA","US","12345","9876543210")
        )
        var operationHrs = listOf<OperationHours>(
            OperationHours("SUN", "9:00 AM", "19:00 PM",null),
            OperationHours("MON", "9:00 AM", "19:00 PM","Close early"),
            OperationHours("TUE", "9:00 AM", "19:00 PM","Close early"),
            OperationHours("WED", "9:00 AM", "19:00 PM","Close early"),
            OperationHours("THU", "9:00 AM", "19:00 PM","Close early"),
            OperationHours("FRI", "9:00 AM", "19:00 PM","Close early"),
            OperationHours("SAT", "9:00 AM", "19:00 PM","Close early")
        )


        `when`(mdmService.locations(Matchers.anyList())).thenAnswer {

            listOf(
                Location("STR1","Store 1","STR1", address.get(0),operationHrs)
            )
         }

    }


    @Test
    fun locations() {
        val names: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
                locations (locationids:["STR1"]) {
                    name
                    
                }
            }
        """.trimIndent(), "data.locations[*].locationid"
        )

        assertThat(names).contains("STR1")
    }

    @Test
    fun locationsWithException() {
        `when`(mdmService.locations(any())).thenThrow(RuntimeException("nothing to see here"))

        val result = dgsQueryExecutor.execute(
            """
             {
                locations (locationids:["STR1"]) {
                    name 
                }
            }
        """.trimIndent()
        )

        assertThat(result.errors).isNotEmpty
        assertThat(result.errors[0].message).isEqualTo("java.lang.RuntimeException: nothing to see here")
    }

    @Test
    fun locationsWithQueryApi() {
        val graphQLQueryRequest =
            GraphQLQueryRequest(
                com.galaxy.mdm.codegen.client.LocationsGraphQLQuery.Builder().build(),
                com.galaxy.mdm.codegen.client.LocationsProjectionRoot().name()
            )
        val titles = dgsQueryExecutor.executeAndExtractJsonPath<List<String>>(
            graphQLQueryRequest.serialize(),
            "data.locations[*].locationid"
        )
        assertThat(titles[0]).contains("STR1")
    }
}