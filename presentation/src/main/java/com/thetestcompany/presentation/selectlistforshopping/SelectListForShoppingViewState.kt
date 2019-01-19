package com.thetestcompany.presentation.selectlistforshopping

import com.thetestcompany.presentation.entities.ShoppingList


data class SelectListForShoppingViewState(
        var lists: List<ShoppingList> = listOf()
)