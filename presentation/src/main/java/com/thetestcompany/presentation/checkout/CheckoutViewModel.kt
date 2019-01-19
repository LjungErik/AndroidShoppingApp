package com.thetestcompany.presentation.checkout

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.usecases.CreateReceiptFromCart
import com.thetestcompany.domain.usecases.GetItemsInCart
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ShoppingCartItem

class CheckoutViewModel(private val getItemsInCart: GetItemsInCart,
                        private val createReceiptFromCart: CreateReceiptFromCart,
                        private val entityMapper: Mapper<ShoppingCartItemEntity, ShoppingCartItem>) : BaseViewModel() {

    val viewState: MutableLiveData<CheckoutViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = CheckoutViewState()
    }

    fun getItemsInCart() {
        addDisposable(getItemsInCart.observable()
                .flatMap { entityMapper.observable(it) }
                .subscribe({ cartItems ->
                    val total = cartItems.sumByDouble { item -> item.totalPrice }
                    val newState = viewState.value?.copy(
                            items = cartItems,
                            total = total
                    )
                    this.viewState.value = newState
                    this.errorState.value = null
                }, {
                    this.errorState.value = it
                }))
    }

    fun createReceipt(name: String, location: String) {
        addDisposable(createReceiptFromCart.create(name, location)
                .subscribe({ receiptId ->
                    this.viewState.value?.let {
                        val newState = viewState.value?.copy(
                            insertedReceiptId = receiptId
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }

                }, {
                    this.errorState.value = it
                }))
    }

}