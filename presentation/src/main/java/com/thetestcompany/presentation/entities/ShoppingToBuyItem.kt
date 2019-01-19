package com.thetestcompany.presentation.entities

import com.thetestcompany.domain.entities.UnitsOfQuantity

data class ShoppingToBuyItem(
        var name: String,
        var unit: UnitsOfQuantity,
        var quantity: Double,
        var boughtQuantity: Double
) {
    val quantityToBuy: Double
            get() {
                return quantity - boughtQuantity
            }
}