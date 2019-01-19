package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingToBuyItemEntity
import com.thetestcompany.presentation.entities.ShoppingToBuyItem
import javax.inject.Inject


class ShoppingToBuyEntityItemMapper @Inject constructor():  Mapper<ShoppingToBuyItemEntity, ShoppingToBuyItem>() {
    override fun mapFrom(from: ShoppingToBuyItemEntity): ShoppingToBuyItem {
        return ShoppingToBuyItem(
                name = from.name,
                unit = from.unit,
                quantity = from.quantity,
                boughtQuantity = from.boughtQuantity
        )
    }

}