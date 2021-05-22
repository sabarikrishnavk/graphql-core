package com.galaxy.promotion.services

import com.galaxy.catalog.codegen.client.SkusGraphQLQuery
import com.galaxy.catalog.codegen.client.SkusProjectionRoot
import com.galaxy.catalog.codegen.types.Sku
import com.galaxy.foundation.context.CustomContext
import com.galaxy.promotion.util.UrlProperties
import com.jayway.jsonpath.TypeRef
import com.netflix.graphql.dgs.client.DefaultGraphQLClient
import com.netflix.graphql.dgs.client.HttpResponse
import com.netflix.graphql.dgs.client.RequestExecutor
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate




@Service
class CatalogService (val restTemplate: RestTemplate , val urlProperties: UrlProperties){
    /**
     * Get sku details
     */


    fun getSkuDetails(skuids: List<String?>?,context: CustomContext)  : List<Sku?>? {


        val graphQLClient = DefaultGraphQLClient(urlProperties.catalog)
        val variables = HashMap<String, Any>();
        variables.put("SKUIDS",skuids!!.toTypedArray())

        val QUERY = "query skuQuery{\n" +
                "  skus(skuids: \"\$SKUIDS\" ){\n" +
                "    name\n" +
                "    price\n" +
                "    skuid\n" +
                "    attributes{\n" +
                "        attributeid\n" +
                "        name\n" +
                "        value\n" +
                "    }\n" +
                "  }\n" +
                "}"
        val requestHeaders = HttpHeaders()
        requestHeaders.set("Authorization",context.bearerToken)
        //headers.forEach(requestHeaders::put)

        val response =
            graphQLClient.executeQuery(QUERY, variables, "skuQuery",
                RequestExecutor { url: String, headers: Map<String, List<String>>, body: String ->

                    /**
                     * Use RestTemplate to call the GraphQL service.
                     * The response type should simply be String, because the parsing will be done by the GraphQLClient.
                     */
                    val exchange: ResponseEntity<String> = restTemplate.exchange(
                        url, HttpMethod.POST, HttpEntity<Any?>(body, requestHeaders),
                        String::class.java
                    )
                    HttpResponse(exchange.statusCodeValue, exchange.body)
                })
        var skus = response.extractValueAsObject("skus", object : TypeRef<List<Sku?>?>() {})
        return skus
    }

}