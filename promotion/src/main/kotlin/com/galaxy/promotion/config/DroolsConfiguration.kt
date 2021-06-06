package com.galaxy.promotion.config

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.engine.*
import com.galaxy.promotion.services.DiscountService
import com.galaxy.promotion.util.PromotionEventType
import org.drools.template.ObjectDataCompiler
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.lang.Exception
import java.util.*

@Configuration
class DroolsConfiguration(val discountService:DiscountService, val eventLogger: EventLogger) {

    @Bean
    fun getKieContainer(): KieContainer? {
        val drl = applyRuleTemplate()
        eventLogger.log(PromotionEventType.PROMO_ENGINE_LOAD,"Loading DRL",drl)

        val kieServices = KieServices.Factory.get()
        val kieFileSystem = kieServices.newKieFileSystem()
        kieFileSystem.write("src/main/resources/rule.drl", drl)
        kieServices.newKieBuilder(kieFileSystem).buildAll()
        val kieContainer = kieServices.newKieContainer(kieServices.repository.defaultReleaseId)

        eventLogger.log(PromotionEventType.PROMO_ENGINE_LOAD,"Load completed ","kieContainer created")
        return kieContainer
    }



    @Throws(Exception::class)
    fun applyRuleTemplate(): String {
        val activeSkuRules = discountService.activeRules();


        eventLogger.log(PromotionEventType.PROMO_ENGINE_LOAD,"Get Rules in applyRuleTemplate ",activeSkuRules)

        var rules = mutableListOf<Map<String, Any>?>();
        activeSkuRules.forEach{rule->

            val ruleMap: MutableMap<String, Any> = HashMap()
            ruleMap["rule"] = rule
            ruleMap["request"] = rule.requestClassName
            ruleMap["discount"] = rule.action.discount
            rules.add(ruleMap);
        }

        return ObjectDataCompiler().compile(
            rules,
            Thread.currentThread().contextClassLoader.getResourceAsStream("promotion-rule-template.drl")
        )
    }
}




//        val kieServices = KieServices.Factory.get()
//        val kieFileSystem = kieServices.newKieFileSystem()
//        kieFileSystem.write("src/main/resources/rule.drl", drl)
//        kieServices.newKieBuilder(kieFileSystem).buildAll()
//        val kieContainer = kieServices.newKieContainer(kieServices.repository.defaultReleaseId)
//        val statelessKieSession = kieContainer.kieBase.newStatelessKieSession()

