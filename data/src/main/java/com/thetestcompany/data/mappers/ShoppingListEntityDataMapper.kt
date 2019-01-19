package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ShoppingListData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingListEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ShoppingListEntityDataMapper @Inject constructor() : Mapper<ShoppingListEntity, ShoppingListData>() {
    override fun mapFrom(from: ShoppingListEntity): ShoppingListData {
        return ShoppingListData(
                id = from.id,
                name = from.name,
                createdAt = from.createdAt,
                color = from.color
        )
    }
}