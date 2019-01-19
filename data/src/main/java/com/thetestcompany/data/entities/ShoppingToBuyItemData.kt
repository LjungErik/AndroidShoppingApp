package com.thetestcompany.data.entities

import com.thetestcompany.domain.entities.UnitsOfQuantity


data class ShoppingToBuyItemData(
        var name: String,
        var unit: UnitsOfQuantity,
        var quantity: Double
) {
    var boughtQuantity: Double = 0.0

    override fun equals(other: Any?): Boolean {
        if(other is ShoppingToBuyItemData) {
            return this.name == other.name && this.unit == other.unit
        }
        return super.equals(other)
    }

    fun equalsCartItem(item: ShoppingCartItemData): Boolean {
        return this.quantity != this.boughtQuantity &&
                item.keywords.contains(this.name.toLowerCase()) &&
                this.unit == item.unit
    }
}