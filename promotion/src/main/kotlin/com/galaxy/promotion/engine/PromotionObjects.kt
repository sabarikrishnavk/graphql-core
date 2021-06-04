package  com.galaxy.promotion.engine
import java.sql.Timestamp

data class PEPromotions(var promotionid: String, var description: String?,
                        var status: PromotionStatus,
                        var startdate: Timestamp?, var enddate: Timestamp? ) {
    public companion object
}

enum class PromotionStatus {
    INACTIVE,
    ACTIVE,
    EXPIRED
}

enum class PromotionType {

    ORDER_PERCENT_OFF,
    ORDER_AMOUNT_OFF,
    ORDER_FREE_GIFT, SHIPPING_FIXED_PRICE,
    SHIPPING_AMOUNT_OFF,
    SKU_SHIPPING_FIXED_PRICE,

    SKU_TOTAL_PERCENT_OFF,
    SKU_TOTAL_AMOUNT_OFF,
    SKU_TOTAL_FIXED_PRICE,

    CROSS_SKU_PERCENT_OFF,
    CROSS_SKU_AMOUNT_OFF,
    CROSS_SKU_FIXED_PRICE,

    SKU_ITEM_PERCENT_OFF,
    SKU_ITEM_AMOUNT_OFF,
    SKU_ITEM_FIXED_PRICE
}


