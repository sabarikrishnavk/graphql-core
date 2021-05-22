package com.galaxy.promotion.engine

import org.kie.api.runtime.KieContainer
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class PromotionEngine(private val kieContainer: KieContainer?) {


    @Throws(Exception::class)
    fun evaluate(requestList: List<PERequest> ): List<PEResult> {

        var results= mutableListOf<PEResult>()
        for (request in requestList) {
            val result = PEResult()

            val kieSession = kieContainer!!.newKieSession()
            kieSession.insert(request)
            kieSession.globals["result"] = result
            kieSession.fireAllRules()
            kieSession.dispose()

            results.add(result)
        }
        return results
    }

}


