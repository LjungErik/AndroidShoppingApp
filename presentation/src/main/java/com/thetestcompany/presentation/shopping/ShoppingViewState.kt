package com.thetestcompany.presentation.shopping

import com.thetestcompany.presentation.entities.ShoppingCartItem


data class ShoppingViewState(
        val total: Double = 0.0,
        var barcodeProduct: ShoppingCartItem? = null,
        var newItem: Boolean = false,
        var startBarcodeCapture: Boolean = false
)