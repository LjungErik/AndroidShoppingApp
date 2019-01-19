package com.thetestcompany.presentation.entities

import com.thetestcompany.domain.entities.UnitsOfQuantity

data class ShoppingListItem(
        var id: Long,
        var name: String,
        var quantity: Double,
        var unit: UnitsOfQuantity,
        var listName: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if(other is ShoppingListItem) {
            return other.id == this.id
        }
        return super.equals(other)
    }
}