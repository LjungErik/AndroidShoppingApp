package com.thetestcompany.domain.entities


data class ShoppingListItemEntity(
        var id: Long,
        var listName: String,
        var name: String,
        var quantity: Double,
        var unit: UnitsOfQuantity
)