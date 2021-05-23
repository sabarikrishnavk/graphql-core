package com.galaxy.promotion.engine

import com.google.gson.Gson
import java.util.ArrayList
import java.util.List

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
        add(gson.fromJson<PEDiscount>(promotion,PEDiscount::class.java))
    }
}
class PEDiscount(
    var promotionid :String ,
    var promotiondescription: String ,
    var discount: Double ,
    var skuid: String? ,
    var shipmodemode: String?
){
    override fun toString(): String {
        return gson.toJson(this).replace("\"","'")
    }
}
class PEAction(
    var discount: PEDiscount
)
