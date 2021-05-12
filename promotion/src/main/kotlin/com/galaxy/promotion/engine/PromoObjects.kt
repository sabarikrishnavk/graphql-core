package com.galaxy.promotion.engine

import com.fasterxml.jackson.annotation.JsonProperty

class PromoObjects {
}

data class PECart(  var cartid: String,var paymenttype: String?, var totalprice: Int? , var discount: Int? ) {
    public companion object
}

data class PECartItem(var skuid: String, var quantity: Int, var shippingCode: String) {
    public companion object
}
data class PEPayment(var paymenttype: String , var amount: Int) {
    public companion object
}
