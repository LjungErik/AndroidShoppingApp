package com.thetestcompany.presentation.shopping

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.usecases.*
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ShoppingCartItem


class ShoppingViewModel(private val getItemsInCart: GetItemsInCart,
                        private val addItemToCart: AddItemToCart,
                        private val removeItemFromCart: RemoveItemFromCart,
                        private val getProductFromBarcodes: GetProductFromBarcodes,
                        private val clearShoppingCart: ClearShoppingCart,
                        private val entityMapper: Mapper<ShoppingCartItemEntity, ShoppingCartItem>,
                        private val itemMapper: Mapper<ShoppingCartItem, ShoppingCartItemEntity>): BaseViewModel() {

    val viewState: MutableLiveData<ShoppingViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ShoppingViewState()
    }

    fun getCartTotal() {
        addDisposable(getItemsInCart.observable()
                .flatMap { entityMapper.observable(it) }
                .subscribe({ cartItems ->
                    val total = cartItems.sumByDouble { item -> item.totalPrice }
                    val newState = this.viewState.value?.copy(
                            total = total,
                            barcodeProduct = null,
                            newItem = false,
                            startBarcodeCapture = true
                    )
                    this.viewState.value = newState
                    this.errorState.value = null
                }, {
                    this.errorState.value = it
                }))
    }

    fun addItemToCart(item: ShoppingCartItem) {
        addDisposable(addItemToCart.add(itemMapper.mapFrom(item))
                .flatMap { entityMapper.observable(it) }
                .subscribe({ cartItem ->
                    this.viewState.value?.let {
                        val newTotal = it.total + cartItem.totalPrice
                        val newState = this.viewState.value?.copy(
                                total = newTotal,
                                barcodeProduct = null,
                                newItem = false,
                                startBarcodeCapture = true
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    this.errorState.value = it
                }))
    }

    fun removeItemFromCart(item: ShoppingCartItem) {
        addDisposable(removeItemFromCart.remove(itemMapper.mapFrom(item))
                .subscribe({
                    this.viewState.value?.let {
                        val newTotal = it.total - item.totalPrice
                        val newState = this.viewState.value?.copy(
                                total = newTotal,
                                barcodeProduct = null,
                                newItem = false,
                                startBarcodeCapture = true
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    this.errorState.value = it
                }))
    }

    fun getProductFromBarcodes(barcodes: List<String>) {
        addDisposable(getProductFromBarcodes.get(barcodes)
                .map { entityMapper.mapOptional(it) }
                .subscribe({ item ->
                    val newState = this.viewState.value?.copy(
                            barcodeProduct = item.value,
                            newItem = true,
                            startBarcodeCapture = false
                    )
                    this.viewState.value = newState
                    this.errorState.value = null
                },{
                    val newState = this.viewState.value?.copy(
                            barcodeProduct = null,
                            newItem = true,
                            startBarcodeCapture = false
                    )
                    this.viewState.value = newState
                    this.errorState.value = it
                }))
    }

    fun clearShoppingCart() {
        addDisposable(clearShoppingCart.observable()
                .subscribe({
                    //Here you should make sure that clear was successful
                }))
    }
}