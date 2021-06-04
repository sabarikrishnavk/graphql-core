package com.galaxy.promotion.engine.objects

import com.google.gson.Gson

/**
 * Result interface and possible results
 */

val gson = Gson()
class PEResult{
    var discounts  = mutableListOf<PEDiscount>()
    fun add(discount: PEDiscount){
        discounts.add(discount)
    }

    fun addPromotion( promotion:String){
        add(gson.fromJson<PEDiscount>(promotion, PEDiscount::class.java))
    }
}
class PEDiscount(
    var promotionid :String ,
    var promotiondescription: String ,
    var discount: Double ,
    var percentage: Double,
    var freesku: String? ,
    var shipmodemode: String?
){
    override fun toString(): String {
        return gson.toJson(this).replace("\"","'")
    }
}
class PEAction(
    var discount: PEDiscount
)
