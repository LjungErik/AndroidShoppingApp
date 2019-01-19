package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.presentation.entities.ShoppingList
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingListEntityMapper @Inject constructor() : Mapper<ShoppingList, ShoppingListEntity>() {
    override fun mapFrom(from: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
                id = from.id,
                name = from.name,
                createdAt = from.createdAt,
                color = from.color
        )
    }

}