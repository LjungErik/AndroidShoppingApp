package com.thetestcompany.domain

import com.thetestcompany.domain.entities.ShoppingListEntity
import io.reactivex.Observable


interface ShoppingListRepository {
    fun getShoppingLists() : Observable<List<ShoppingListEntity>>
    fun insertShoppingList(shoppingListEntity: ShoppingListEntity) : Observable<Long>
    fun insertShoppingLists(shoppingListsEntity: List<ShoppingListEntity>): Observable<List<Long>>
    fun deleteShoppingLists(shoppingListEntities: List<ShoppingListEntity>)
}