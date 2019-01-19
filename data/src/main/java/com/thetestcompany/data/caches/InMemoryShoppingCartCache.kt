package com.thetestcompany.data.caches

import com.thetestcompany.data.entities.ShoppingCartItemData


class InMemoryShoppingCartCache {

    private val cart: MutableList<ShoppingCartItemData> = mutableListOf()

    fun add(item: ShoppingCartItemData) {
        val index = cart.indexOfFirst { item.barcodeId == it.barcodeId }
        if(index != -1) {
            cart[index].quantity += item.quantity
        }
        else {
            cart.add(item)
        }
    }

    fun remove(item: ShoppingCartItemData) {
        val index = cart.indexOfFirst { item.barcodeId == it.barcodeId }
        cart[index].quantity -= item.quantity

        if(cart[index].quantity <= 0) {
            cart.removeAt(index)
        }
    }

    fun getCart(): List<ShoppingCartItemData> {
        return cart
    }

    fun clear() {
        cart.clear()
    }
}