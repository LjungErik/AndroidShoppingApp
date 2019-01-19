package com.thetestcompany.data.db

import android.arch.persistence.room.*
import com.thetestcompany.data.entities.ShoppingListItemData
import com.thetestcompany.data.entities.ShoppingToBuyItemData


@Dao
interface ShoppingListItemDao {

    @Query("SELECT * FROM ShoppingListItem WHERE listName = :list")
    fun getShoppingListItems(list: String) : List<ShoppingListItemData>

    @Query("SELECT name, unit, sum(quantity) as quantity, 0.0 as boughtQuantity " +
           "FROM ShoppingListItem WHERE listName in (:listNames) GROUP BY name, unit")
    fun getGroupedListItems(listNames: Array<String>): List<ShoppingToBuyItemData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoppingListItem(shoppingListItemData: ShoppingListItemData): Long

    @Delete
    fun deleteShoppingListItem(shoppingListItemData: ShoppingListItemData)

    @Update
    fun updateShoppingListItem(shoppingListItemData: ShoppingListItemData)
}