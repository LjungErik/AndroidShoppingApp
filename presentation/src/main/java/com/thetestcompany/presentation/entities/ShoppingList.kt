package com.thetestcompany.presentation.entities

import java.util.*

data class ShoppingList(
        var id: Long,
        var name: String,
        var createdAt: Date,
        var color: Int
) {
    override fun equals(other: Any?): Boolean {
        if(other is ShoppingList) {
            return other.id == this.id
        }
        return super.equals(other)
    }
}