package  com.galaxy.promotion.engine
import java.sql.Timestamp

data class PEPromotions(var promotionId: String, var description: String?,
                        var status: PromotionStatus,
                        var startDate: Timestamp?, var endDate: Timestamp? ) {
    public companion object
}

enum class PromotionStatus {
    INACTIVE,
    ACTIVE,
    EXPIRED
}


