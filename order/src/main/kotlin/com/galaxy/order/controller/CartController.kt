package com.galaxy.order.controller

import com.galaxy.foundation.logger.EventLogger
import com.galaxy.order.codegen.types.Cart
import com.galaxy.order.codegen.types.ReturnCart
import com.galaxy.order.services.CartService
import com.galaxy.order.util.OrderEvent
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class CartController(private val cartService: CartService, val eventLogger: EventLogger) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */

    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun findcart(@InputArgument cartid : String): ReturnCart {
        eventLogger.log( OrderEvent.CART_FIND, "find cart", cartid)
        return cartService.getCart(cartid)
    }
}