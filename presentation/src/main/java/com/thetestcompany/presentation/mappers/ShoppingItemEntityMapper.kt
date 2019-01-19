package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import com.thetestcompany.presentation.entities.ShoppingListItem
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingItemEntityMapper @Inject constructor(): Mapper<ShoppingListItem, ShoppingListItemEntity>() {
    override fun mapFrom(from: ShoppingListItem): ShoppingListItemEntity {
        return ShoppingListItemEntity(
                id = from.id,
                name = from.name,
                quantity = from.quantity,
                unit = from.unit,
                listName = from.listName
        )
    }

}