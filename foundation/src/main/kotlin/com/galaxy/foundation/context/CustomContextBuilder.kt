package com.galaxy.foundation.context

import com.netflix.graphql.dgs.context.DgsCustomContextBuilder
import org.springframework.stereotype.Component


@Component
class CustomContextBuilder : DgsCustomContextBuilder<CustomContext?> {
    override fun build(): CustomContext {
        return CustomContext()
    }
}

class CustomContext {
    val userId = "UserId"
    val storeId= "EcomStoreId"
}