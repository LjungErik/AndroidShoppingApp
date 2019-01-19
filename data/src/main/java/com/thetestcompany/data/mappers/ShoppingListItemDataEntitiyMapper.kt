package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ShoppingListItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingListItemDataEntitiyMapper @Inject constructor() : Mapper<ShoppingListItemData, ShoppingListItemEntity>() {

    override fun mapFrom(from: ShoppingListItemData): ShoppingListItemEntity {
        return ShoppingListItemEntity(
                id = from.id,
                listName = from.listName,
                name = from.name,
                quantity = from.quantity,
                unit = from.unit
        )
    }
}