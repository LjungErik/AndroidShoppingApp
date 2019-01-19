package com.thetestcompany.presentation.shopping.shoppingcart

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.usecases.GetItemsInCart
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ShoppingCartItem


class ShoppingCartViewModel(private val getItemsInCart: GetItemsInCart,
                            private val entityMapper: Mapper<ShoppingCartItemEntity, ShoppingCartItem>): BaseViewModel() {

    val viewState: MutableLiveData<ShoppingCartViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ShoppingCartViewState()
    }

    fun getItemsInCart() {
        addDisposable(getItemsInCart.observable()
                .flatMap { entityMapper.observable(it) }
                .subscribe({ cartItems ->
                    val newState = viewState.value?.copy(
                            items = cartItems
                    )
                    this.viewState.value = newState
                    this.errorState.value = null
                }, {
                    this.errorState.value = it
                }))
    }
}