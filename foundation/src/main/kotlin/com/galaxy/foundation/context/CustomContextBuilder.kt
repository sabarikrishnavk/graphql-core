package com.galaxy.foundation.context

import com.galaxy.foundation.JwtUtils
import com.netflix.graphql.dgs.context.DgsCustomContextBuilder
import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest


@Component
class CustomContextBuilder : DgsCustomContextBuilderWithRequest<CustomContext?> {

    @Autowired
    private val jwtTokenUtil: JwtUtils? = null

    override fun build(extensions: Map<String, Any>?, headers: HttpHeaders?, webRequest: WebRequest?): CustomContext? {
        var context = CustomContext()

        val requestTokenHeader = headers?.get("Authorization")?.get(0)
        val jwtToken = requestTokenHeader?.substring(7)
        val jwtUser = jwtTokenUtil?.getJwtUser(jwtToken)
        context.bearerToken =  requestTokenHeader
        context.userId = jwtUser?.userId.toString()
        return context
    }


}

class CustomContext {
    var userId :String? =null
    var location:String? =null
    var channel:String? =null
    var bearerToken:String? =null
}