package com.galaxy.promotion.engine


open class PERequest() {
    var price: Double? = null
    var location: String? = null
    var attr:Map<String,String>? = mapOf();
}

class PEOrder(var orderid: String ,var totalsku: Double? , var totalshipping: Double? ,var totaldiscount: Double?, var total: Double?  ) : PERequest()
class PESkuRequest(var skuid: String, var quantity: Double, var shippingcode: String): PERequest()
class PEPayment(var paymenttype: String , var amount: Double) : PERequest()
class PEShipping(var shippingcode: String , var amount: Double) : PERequest()