package com.galaxy.foundation.context

import com.netflix.graphql.dgs.context.DgsCustomContextBuilder
import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest


@Component
class CustomContextBuilder : DgsCustomContextBuilderWithRequest<CustomContext?> {

    override fun build(extensions: Map<String, Any>?, headers: HttpHeaders?, webRequest: WebRequest?): CustomContext? {
        var context = CustomContext()
        context.bearerToken = headers!!.get("Authorization")!!.get(0)
        return context
    }


}

class CustomContext {
    var userId = "UserId"
    var storeId= "EcomStoreId"
    var channel= "desktop"
    var bearerToken = "Bearer Token"
}