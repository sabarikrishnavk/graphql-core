package com.galaxy.promotion.engine

import java.sql.Timestamp

class PromotionClientService {

    //Get promotions for the list of SKUs
    fun getSKUPromotions( skus: List<String>) : List<PEPromotions> {
        return listOf()
    }

    fun getActiveSkuPromotions(location:String, date:Timestamp) : List<PEPromotions>{
        return listOf()
    }
}