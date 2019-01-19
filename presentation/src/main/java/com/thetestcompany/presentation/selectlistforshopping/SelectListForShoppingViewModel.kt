package com.thetestcompany.presentation.selectlistforshopping

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.usecases.GetShoppingLists
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ShoppingList


class SelectListForShoppingViewModel(private val getShoppingLists: GetShoppingLists,
                                     private val entityMapper: Mapper<ShoppingListEntity, ShoppingList>) : BaseViewModel() {

    val viewState: MutableLiveData<SelectListForShoppingViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = SelectListForShoppingViewState()
    }

    fun getShoppingLists() {
        addDisposable(getShoppingLists.observable()
                .flatMap { entityMapper.observable(it) }
                .subscribe({ shoppingLists ->
                    viewState.value?.let {
                        val newState = this.viewState.value?.copy(
                                lists = shoppingLists
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }
}