package com.galaxy.promotion.engine

import com.google.gson.Gson

/**
 * Result interface and possible results
 */

val gson = Gson()
class PEResult{
    var discounts = mutableListOf<PEDiscount>()
    fun add(discount: PEDiscount){
        discounts.add(discount)
    }

    fun addPromotion( promotion:String){
        println("promotion:: ${promotion}")
        add(gson.fromJson<PEDiscount>(promotion,PEDiscount::class.java))
    }
}
class PEDiscount(
    var promotionid :String ,
    var promotiondescription: String ,
    var discount: Double ,
    var skuid: String? ,
    var shipmodeid: String?
){
    override fun toString(): String {
        return gson.toJson(this).replace("\"","'")
    }
}
class PEAction(
    var discount: PEDiscount
)
