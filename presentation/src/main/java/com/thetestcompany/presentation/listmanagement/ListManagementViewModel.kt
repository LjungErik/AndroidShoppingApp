package com.thetestcompany.presentation.listmanagement

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.usecases.AddShoppingList
import com.thetestcompany.domain.usecases.AddShoppingLists
import com.thetestcompany.domain.usecases.GetShoppingLists
import com.thetestcompany.domain.usecases.RemoveShoppingLists
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ShoppingList


class ListManagementViewModel(private val getShoppingLists: GetShoppingLists,
                              private val addShoppingList: AddShoppingList,
                              private val addShoppingLists: AddShoppingLists,
                              private val removeShoppingLists: RemoveShoppingLists,
                              private val entityMapper: Mapper<ShoppingListEntity, ShoppingList>,
                              private val listMapper: Mapper<ShoppingList, ShoppingListEntity>): BaseViewModel() {

    val viewState: MutableLiveData<ListManagementViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ListManagementViewState()
    }

    fun getShoppingLists() {
        addDisposable(getShoppingLists.observable()
                .flatMap { entityMapper.observable(it) }
                .subscribe({ shoppingLists ->
                    viewState.value?.let {
                        val newState = this.viewState.value?.copy(
                                lists = shoppingLists,
                                deletedLists = null
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }

    fun addShoppingList(shoppingList: ShoppingList) {
        addDisposable(addShoppingList.add(listMapper.mapFrom(shoppingList))
                .flatMap { entityMapper.observable(it) }
                .subscribe({ list ->
                    viewState.value?.let {
                        val shoppingLists:MutableList<ShoppingList> = it.lists.toMutableList()
                        shoppingLists.add(list)
                        val newState = this.viewState.value?.copy(
                                lists = shoppingLists,
                                deletedLists = null
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }

    fun addShoppingLists(shoppingListsToAdd: List<ShoppingList>) {
        addDisposable(addShoppingLists.add(shoppingListsToAdd.map{ listMapper.mapFrom(it) })
                .flatMap { entityMapper.observable(it) }
                .subscribe({ lists ->
                    viewState.value?.let {
                        val shoppingLists:MutableList<ShoppingList> = it.lists.toMutableList()
                        shoppingLists.addAll(lists)
                        val newState = this.viewState.value?.copy(
                                lists = shoppingLists,
                                deletedLists = null
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))

    }

    fun removeShoppingLists(shoppingLists: List<ShoppingList>) {
        addDisposable(removeShoppingLists.remove(shoppingLists.map { listMapper.mapFrom(it) })
                .subscribe({
                    viewState.value?.let {
                        val lists = it.lists.takeWhile { list -> shoppingLists.firstOrNull { entry -> entry == list } == null }
                        val newState = this.viewState.value?.copy(
                                lists = lists,
                                deletedLists = shoppingLists
                        )
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }
}