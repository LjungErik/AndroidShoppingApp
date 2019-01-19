package com.thetestcompany.data.db

import android.arch.persistence.room.*
import com.thetestcompany.data.entities.ShoppingListData


@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM ShoppingList")
    fun getShoppingLists() : List<ShoppingListData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoppingList(shoppingListData: ShoppingListData): Long

    @Insert
    fun insertShoppingLists(shoppingLists: List<ShoppingListData>): List<Long>

    @Delete
    fun deleteShoppingLists(shoppingLists: List<ShoppingListData>)
}