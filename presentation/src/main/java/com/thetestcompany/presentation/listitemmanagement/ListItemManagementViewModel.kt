package com.thetestcompany.presentation.listitemmanagement

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import com.thetestcompany.domain.usecases.*
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ShoppingListItem

class ListItemManagementViewModel(private val getShoppingListItems: GetShoppingListItems,
                                  private val removeShoppingListItem: RemoveShoppingListItem,
                                  private val updateShoppingListItem: UpdateShoppingListItem,
                                  private val addShoppingListItem: AddShoppingListItem,
                                  private val entityMapper: Mapper<ShoppingListItemEntity, ShoppingListItem>,
                                  private val itemMapper: Mapper<ShoppingListItem, ShoppingListItemEntity>) : BaseViewModel() {

    val viewState: MutableLiveData<ListItemManagementViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ListItemManagementViewState()
    }

    fun getShoppingListItems(listName: String) {
        addDisposable(getShoppingListItems.get(listName)
                .flatMap { entityMapper.observable(it) }
                .subscribe({ shoppingListItems ->
                    viewState.value?.let{
                        val newState = this.viewState.value?.copy(
                                items = shoppingListItems,
                                deletedItem = null)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }

    fun removeShoppingListItem(item: ShoppingListItem) {
        addDisposable(removeShoppingListItem.remove(itemMapper.mapFrom(item))
                .subscribe({
                    viewState.value?.let {
                        val items = it.items.takeWhile { i -> i != item }
                        val newState = this.viewState.value?.copy(
                                items = items,
                                deletedItem = item)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }

    fun saveShoppingListItem(item: ShoppingListItem) {
        if(item.id == 0L) {
            addShoppingListItem(item)
        }
        else {
            updateShoppingListItem(item)
        }
    }

    private fun updateShoppingListItem(item: ShoppingListItem) {
        addDisposable(updateShoppingListItem.update(itemMapper.mapFrom(item))
                .subscribe({
                    viewState.value?.let {
                        val items = it.items.takeWhile { i -> i != item }.toMutableList()
                        items.add(item)
                        val newState = this.viewState.value?.copy(
                                items = items,
                                deletedItem = null)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }

    private fun addShoppingListItem(item: ShoppingListItem) {
        addDisposable(addShoppingListItem.add(itemMapper.mapFrom(item))
                .flatMap { entityMapper.observable(it) }
                .subscribe({
                    viewState.value?.let {
                        val items = it.items.toMutableList()
                        items.add(item)
                        val newState = this.viewState.value?.copy(
                                items = items,
                                deletedItem = null)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }
}