package com.galaxy.promotion.engine

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.promotion.codegen.types.Promotion
import com.galaxy.promotion.services.DiscountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class PromotionCache(private val discountService: DiscountService, val eventLogger: EventLogger) {

    fun getPromotion(promotionid: String): Promotion {
        return discountService.getPromotion(promotionid)
    }

}
