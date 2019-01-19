package com.thetestcompany.presentation.shopping.shoppingcart

import com.thetestcompany.presentation.entities.ShoppingCartItem


data class ShoppingCartViewState(
        val items: List<ShoppingCartItem> = listOf()
)