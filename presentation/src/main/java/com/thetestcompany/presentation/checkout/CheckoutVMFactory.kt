package com.thetestcompany.presentation.checkout

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.usecases.CreateReceiptFromCart
import com.thetestcompany.domain.usecases.GetItemsInCart
import com.thetestcompany.presentation.entities.ShoppingCartItem

class CheckoutVMFactory(private val getItemsInCart: GetItemsInCart,
                        private val createReceiptFromCart: CreateReceiptFromCart,
                        private val entityMapper: Mapper<ShoppingCartItemEntity, ShoppingCartItem>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(
                    getItemsInCart,
                    createReceiptFromCart,
                    entityMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}