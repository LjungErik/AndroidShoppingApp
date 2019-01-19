package com.thetestcompany.presentation.listitemmanagement

import com.thetestcompany.presentation.entities.ShoppingListItem

data class ListItemManagementViewState(
    var items: List<ShoppingListItem> = listOf(),
    var deletedItem: ShoppingListItem? = null
)