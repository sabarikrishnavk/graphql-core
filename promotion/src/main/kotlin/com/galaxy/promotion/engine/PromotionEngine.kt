package com.galaxy.promotion.engine

import org.kie.api.runtime.KieContainer
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class PromotionEngine(private val kieContainer: KieContainer?) {


    @Throws(Exception::class)
    fun evaluate(request: PERequest ): PEResult {
        val result = PEResult()
        val kieSession = kieContainer!!.newKieSession()
        kieSession.insert(request)
        kieSession.globals["result"] = result
        kieSession.fireAllRules()
        kieSession.dispose()
        return result
    }

}


