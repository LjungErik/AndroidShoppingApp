package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import com.thetestcompany.presentation.entities.ShoppingListItem
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingEntityItemMapper @Inject constructor(): Mapper<ShoppingListItemEntity, ShoppingListItem>() {
    override fun mapFrom(from: ShoppingListItemEntity): ShoppingListItem {
        return ShoppingListItem(
                id = from.id,
                name = from.name,
                quantity = from.quantity,
                unit = from.unit,
                listName = from.listName
        )
    }

}