package com.thetestcompany.presentation.shopping.shoppingtobuy

import com.thetestcompany.presentation.entities.ShoppingToBuyItem


data class ShoppingToBuyViewState(
        val items: List<ShoppingToBuyItem> = listOf()
)