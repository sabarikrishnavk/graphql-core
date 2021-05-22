package com.galaxy.promotion.service

import com.galaxy.catalog.codegen.types.Sku
import com.galaxy.foundation.context.CustomContext
import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.services.CatalogService
import com.galaxy.promotion.services.PromotionService
import com.galaxy.promotion.util.UrlProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import org.springframework.web.client.RestTemplate

@SpringBootTest(classes = [CatalogService::class, EventLogger::class,RestTemplate::class])
@EnableConfigurationProperties(UrlProperties::class)
@TestPropertySource("classpath:application.properties")
class CatalogServiceTest {
    @Autowired
    lateinit var  eventLogger: EventLogger
    @Autowired
    lateinit var catalogService: CatalogService
    @Autowired
    lateinit var restTemplate: RestTemplate


    @Test
    fun evalulateSkuRequest() {
        val skuids = listOf<String>("SKU1","SKU2")
        val context = CustomContext()
        context.bearerToken ="Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJHYWxheHkiLCJzdWIiOiJ0ZXN0dXNlciIsIm5hbWUiOiJ0ZXN0dXNlciIsInVzZXJJZCI6ImY2OTE1NjJkLWY2ZDMtNDE4Zi1hMWMzLTJiNTFjYTI4NTRjNCIsInJvbGUiOiJSRUdJU1RFUkVEIiwiZW1haWwiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjIxNDU2NTY1LCJleHAiOjE2MjIwNjEzNjV9.gZqZATF-Y33EJxPMyAxkkewGyVdAtXR3P9ON2xOLE5VD5Q4iCms-oFqVEoo2LZ3vkTwF67ELP9O9t4pjmVn79w"
        val skus= catalogService.getSkuDetails(skuids,context)
        for (sku in skus!!.listIterator()){
            println(sku)
        }

    }
}