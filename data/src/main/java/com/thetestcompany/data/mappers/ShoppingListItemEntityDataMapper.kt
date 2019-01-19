package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ShoppingListItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingListItemEntityDataMapper @Inject constructor(): Mapper<ShoppingListItemEntity, ShoppingListItemData>() {
    override fun mapFrom(from: ShoppingListItemEntity): ShoppingListItemData {
        return ShoppingListItemData(
                id = from.id,
                listName = from.listName,
                name =  from.name,
                quantity = from.quantity,
                unit = from.unit
        )
    }
}