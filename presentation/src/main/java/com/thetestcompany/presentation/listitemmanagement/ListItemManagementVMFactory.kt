package com.thetestcompany.presentation.listitemmanagement

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import com.thetestcompany.domain.usecases.AddShoppingListItem
import com.thetestcompany.domain.usecases.GetShoppingListItems
import com.thetestcompany.domain.usecases.RemoveShoppingListItem
import com.thetestcompany.domain.usecases.UpdateShoppingListItem
import com.thetestcompany.presentation.entities.ShoppingListItem

class ListItemManagementVMFactory(private val getShoppingListItems: GetShoppingListItems,
                                  private val removeShoppingListItem: RemoveShoppingListItem,
                                  private val updateShoppingListItem: UpdateShoppingListItem,
                                  private val addShoppingListItem: AddShoppingListItem,
                                  private val entityMapper: Mapper<ShoppingListItemEntity, ShoppingListItem>,
                                  private val itemMapper: Mapper<ShoppingListItem, ShoppingListItemEntity>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListItemManagementViewModel::class.java)) {
            return ListItemManagementViewModel(
                    getShoppingListItems,
                    removeShoppingListItem,
                    updateShoppingListItem,
                    addShoppingListItem,
                    entityMapper,
                    itemMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}