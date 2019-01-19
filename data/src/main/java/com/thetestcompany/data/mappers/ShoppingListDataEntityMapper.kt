package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ShoppingListData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingListDataEntityMapper @Inject constructor() : Mapper<ShoppingListData, ShoppingListEntity>() {

    override fun mapFrom(from: ShoppingListData): ShoppingListEntity {
        return ShoppingListEntity(
                id = from.id,
                name = from.name,
                createdAt = from.createdAt,
                color = from.color
        )
    }
}