package com.thetestcompany.presentation.selectlistforshopping

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.usecases.GetShoppingLists
import com.thetestcompany.presentation.entities.ShoppingList


class SelectListForShoppingVMFactory(private val getShoppingLists: GetShoppingLists,
                                     private val entityMapper: Mapper<ShoppingListEntity, ShoppingList>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SelectListForShoppingViewModel::class.java)) {
            return SelectListForShoppingViewModel(
                    getShoppingLists,
                    entityMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}