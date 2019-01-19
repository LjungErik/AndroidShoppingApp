package com.thetestcompany.presentation.listmanagement

import com.thetestcompany.presentation.entities.ShoppingList


data class ListManagementViewState(
        var lists: List<ShoppingList> = listOf(),
        var deletedLists: List<ShoppingList>? = null
)