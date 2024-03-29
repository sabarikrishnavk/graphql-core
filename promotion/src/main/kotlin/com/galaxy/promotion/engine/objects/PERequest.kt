package com.galaxy.promotion.engine.objects


open class PERequest() {
    var price: Double = 0.0
    var location: String? = null
    var attr:Map<String,String>? = mapOf();
}

class PEOrderRequest(var totalskuprice: Double , var totalshipping: Double ,var totaldiscount: Double, var total: Double  ) : PERequest()
class PESkuRequest(var skuid: String, var quantity: Double, var shipmode: String): PERequest()
class PEPayment(var paymenttype: String , var amount: Double) : PERequest()
class PEShipping(var shipmode: String , var amount: Double) : PERequest()