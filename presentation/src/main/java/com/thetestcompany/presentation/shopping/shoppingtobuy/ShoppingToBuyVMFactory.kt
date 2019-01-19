package com.thetestcompany.presentation.shopping.shoppingtobuy

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingToBuyItemEntity
import com.thetestcompany.domain.usecases.GetItemsToBuy
import com.thetestcompany.presentation.entities.ShoppingToBuyItem


class ShoppingToBuyVMFactory(private val getItemsToBuy: GetItemsToBuy,
                             private val entityMapper: Mapper<ShoppingToBuyItemEntity, ShoppingToBuyItem>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingToBuyViewModel::class.java)) {
            return  ShoppingToBuyViewModel(
                    getItemsToBuy,
                    entityMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}