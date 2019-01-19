package com.thetestcompany.presentation.shopping

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.usecases.*
import com.thetestcompany.presentation.entities.ShoppingCartItem


class ShoppingVMFactory(
        private val getItemsInCart: GetItemsInCart,
        private val addItemToCart: AddItemToCart,
        private val removeItemFromCart: RemoveItemFromCart,
        private val getProductFromBarcodes: GetProductFromBarcodes,
        private val clearShoppingCart: ClearShoppingCart,
        private val entityMapper: Mapper<ShoppingCartItemEntity, ShoppingCartItem>,
        private val itemMapper: Mapper<ShoppingCartItem, ShoppingCartItemEntity>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            return  ShoppingViewModel(
                    getItemsInCart,
                    addItemToCart,
                    removeItemFromCart,
                    getProductFromBarcodes,
                    clearShoppingCart,
                    entityMapper,
                    itemMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}