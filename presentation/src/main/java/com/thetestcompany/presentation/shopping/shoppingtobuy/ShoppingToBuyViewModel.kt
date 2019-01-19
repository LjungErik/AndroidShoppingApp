package com.thetestcompany.presentation.shopping.shoppingtobuy

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingToBuyItemEntity
import com.thetestcompany.domain.usecases.GetItemsToBuy
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ShoppingToBuyItem


class ShoppingToBuyViewModel(private val getItemsToBuy: GetItemsToBuy,
                             private val entityMapper: Mapper<ShoppingToBuyItemEntity, ShoppingToBuyItem>): BaseViewModel() {

    val viewState: MutableLiveData<ShoppingToBuyViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ShoppingToBuyViewState()
    }

    fun getItemsToBuy(listNames: Array<String>) {
        addDisposable(getItemsToBuy.get(listNames)
                .flatMap { entityMapper.observable(it) }
                .subscribe({ items ->
                    val newState = this.viewState.value?.copy(items = items)
                    this.viewState.value = newState
                    this.errorState.value = null
                }, {
                    this.errorState.value = it
                }))
    }
}