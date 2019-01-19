package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ShoppingToBuyItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingToBuyItemEntity
import javax.inject.Inject


class ShoppingToBuyDataEntityMapper @Inject constructor(): Mapper<ShoppingToBuyItemData, ShoppingToBuyItemEntity>() {
    override fun mapFrom(from: ShoppingToBuyItemData): ShoppingToBuyItemEntity {
        return ShoppingToBuyItemEntity(
                name = from.name,
                quantity = from.quantity,
                unit = from.unit,
                boughtQuantity = from.boughtQuantity
        )
    }

}