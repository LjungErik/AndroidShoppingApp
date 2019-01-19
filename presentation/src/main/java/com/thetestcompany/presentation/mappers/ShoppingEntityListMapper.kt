package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.presentation.entities.ShoppingList
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingEntityListMapper @Inject constructor() : Mapper<ShoppingListEntity, ShoppingList>() {
    override fun mapFrom(from: ShoppingListEntity): ShoppingList {
        return ShoppingList(
                id = from.id,
                name = from.name,
                createdAt = from.createdAt,
                color = from.color
        )
    }
}