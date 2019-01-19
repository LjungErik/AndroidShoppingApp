package com.thetestcompany.domain

import com.thetestcompany.domain.entities.ShoppingListItemEntity
import io.reactivex.Observable


interface ShoppingListItemRepository {
    fun getShoppingItems(listName: String): Observable<List<ShoppingListItemEntity>>
    fun insertShoppingItem(shoppingListItemEntity: ShoppingListItemEntity): Observable<Long>
    fun deleteShoppingItem(shoppingListItemEntity: ShoppingListItemEntity)
    fun updateShoppingItem(shoppingListItemEntity: ShoppingListItemEntity)
}