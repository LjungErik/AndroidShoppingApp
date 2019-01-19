package com.thetestcompany.presentation.entities

import com.thetestcompany.domain.entities.UnitsOfQuantity

data class ShoppingCartItem(
        var barcodeId: String,
        var name: String,
        var quantity: Double,
        var unit: UnitsOfQuantity,
        var unitPrice: Double,
        var type: String,
        var keywords: Array<String>
) {
    val totalPrice: Double
            get() {
                return quantity * unitPrice
            }
}