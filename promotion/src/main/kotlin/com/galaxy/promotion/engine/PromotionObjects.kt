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


