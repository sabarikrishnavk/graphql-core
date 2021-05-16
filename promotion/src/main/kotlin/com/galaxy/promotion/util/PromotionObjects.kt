package com.galaxy.promotion.util

import com.galaxy.foundation.logger.EventType

class PromotionObjects {
}
enum class PromotionEventType: EventType {
    PROMO_SAVE ,
    PROMO_UPDATE,
    PROMO_FIND,
    PROMO_ENGINE_LOAD,
    PROMO_ENGINE_INVOKE,
    PROMO_ENGINE_CALCULATE,
    PROMO_ENGINE_DATACOLLECTION

}