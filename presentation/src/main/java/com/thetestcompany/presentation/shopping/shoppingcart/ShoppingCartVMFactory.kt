package com.thetestcompany.presentation.shopping.shoppingcart

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.usecases.GetItemsInCart
import com.thetestcompany.presentation.entities.ShoppingCartItem


class ShoppingCartVMFactory(private val getItemsInCart: GetItemsInCart,
                            private val entityMapper: Mapper<ShoppingCartItemEntity, ShoppingCartItem>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            return  ShoppingCartViewModel(
                    getItemsInCart,
                    entityMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}