package com.thetestcompany.presentation.checkout

import com.thetestcompany.presentation.entities.ShoppingCartItem


data class CheckoutViewState(
        val items: List<ShoppingCartItem> = listOf(),
        val total: Double = 0.0,
        val insertedReceiptId: Long? = null
)