package com.thetestcompany.presentation.listmanagement

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.usecases.AddShoppingList
import com.thetestcompany.domain.usecases.AddShoppingLists
import com.thetestcompany.domain.usecases.GetShoppingLists
import com.thetestcompany.domain.usecases.RemoveShoppingLists
import com.thetestcompany.presentation.entities.ShoppingList


class ListManagementVMFactory(private val getShoppingLists: GetShoppingLists,
                              private val addShoppingList: AddShoppingList,
                              private val addShoppingLists: AddShoppingLists,
                              private val removeShoppingLists: RemoveShoppingLists,
                              private val entityMapper: Mapper<ShoppingListEntity, ShoppingList>,
                              private val listMapper: Mapper<ShoppingList, ShoppingListEntity>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListManagementViewModel::class.java)) {
            return ListManagementViewModel(
                    getShoppingLists,
                    addShoppingList,
                    addShoppingLists,
                    removeShoppingLists,
                    entityMapper,
                    listMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}