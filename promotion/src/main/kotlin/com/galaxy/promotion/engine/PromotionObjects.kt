package  com.galaxy.promotion.engine
import java.sql.Timestamp

//data class PEPromotions(var promotionid: String, var description: String?,
//                        var status: PromotionStatus,
//                        var startdate: Timestamp?, var enddate: Timestamp? ) {
//    public companion object
//}

enum class PROMOTION_VARIABLES(s: String) {
    ORDER_TOTAL("total"),
    ORDER_SHIPPING("totalskuprice"),
    ORDER_SKUPRICE("totalskuprice"),
    ORDER_DISCOUNT("totaldiscount"),

    ITEM_PRICE("price"),
    ITEM_QUANTITY("quantity"),
    ITEM_SHIPMODE("shipmode"),
    ITEM_SKUID("skuid")
}



