package com.thetestcompany.domain

import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import io.reactivex.Observable


interface ShoppingCartItemRepository {
    fun getItemsInCart() : Observable<List<ShoppingCartItemEntity>>
    fun addItemToCart(shoppingCartItemEntity: ShoppingCartItemEntity)
    fun removeItemFromCart(shoppingCartItemEntity: ShoppingCartItemEntity)
    fun clearShoppingCart()
}