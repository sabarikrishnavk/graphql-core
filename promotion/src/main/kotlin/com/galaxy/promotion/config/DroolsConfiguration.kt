package com.galaxy.promotion.config

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.engine.*
import com.galaxy.promotion.services.PromotionService
import com.galaxy.promotion.util.PromotionEventType
import org.drools.template.ObjectDataCompiler
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.lang.Exception
import java.util.*

@Configuration
class DroolsConfiguration(val promotionService: PromotionService, val eventLogger: EventLogger) {
    private val kieServices = KieServices.Factory.get()


    @Bean
    fun getKieContainer(): KieContainer? {
        val kieFileSystem = kieServices.newKieFileSystem()

        val prRulesList = promotionService.getCurrentPRRules();

        val drl = applyRuleTemplate(prRulesList)

        eventLogger.log(PromotionEventType.PROMO_ENGINE_LOAD,"Loading DRL",drl)
        print("${drl}");

//        kieFileSystem.write(ResourceFactory.newClassPathResource("discount.drl"))
        val kb = kieServices.newKieBuilder(kieFileSystem)
        kb.buildAll()
        val kieModule = kb.kieModule


        eventLogger.log(PromotionEventType.PROMO_ENGINE_LOAD,"Load completed ","kieModule created")
        return kieServices.newKieContainer(kieModule.releaseId)
    }



    @Throws(Exception::class)
    fun applyRuleTemplate( prRules : List<PERule>): String {

        var rules = mutableListOf<Map<String, Any>?>();
        prRules.forEach{rule->

            val ruleMap: MutableMap<String, Any> = HashMap()
            ruleMap["rule"] = rule
            ruleMap["request"] = rule.requestClassName
            ruleMap["discount"] = rule.action!!.discount
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
//        //println("drl:\n$drl")
//        kieServices.newKieBuilder(kieFileSystem).buildAll()
//        val kieContainer = kieServices.newKieContainer(kieServices.repository.defaultReleaseId)
//        val statelessKieSession = kieContainer.kieBase.newStatelessKieSession()

