package com.thetestcompany.domain.entities


data class ShoppingToBuyItemEntity(
        var name: String,
        var unit: UnitsOfQuantity,
        var quantity: Double,
        var boughtQuantity: Double
)