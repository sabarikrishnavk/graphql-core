package com.galaxy.promotion.engine


open class PERequest() {

    var price: Double? = null
    var customer: String? = null
}




class PEOrder(  var orderid: String, var totalprice: Double? , var discount: Double? ) : PERequest(){

}

class PESkuRequest(var skuid: String, var quantity: Double, var shippingcode: String): PERequest() {

}
class PEPayment(var paymenttype: String , var amount: Double) : PERequest(){

}
class PEShipping(var shippingcode: String , var amount: Double) : PERequest(){

}
