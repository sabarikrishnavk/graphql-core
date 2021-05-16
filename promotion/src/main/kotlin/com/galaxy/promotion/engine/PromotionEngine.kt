package com.galaxy.promotion.engine

import org.kie.api.runtime.KieContainer
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class PromotionEngine(private val kieContainer: KieContainer?) {


    @Throws(Exception::class)
    fun evaluate(request: PERequest ): PEResult {

        val statelessKieSession = kieContainer!!.kieBase.newStatelessKieSession()
        val result = PEResult()
        statelessKieSession.globals["result"] = result
        statelessKieSession.execute(request)
        return result
    }

}


