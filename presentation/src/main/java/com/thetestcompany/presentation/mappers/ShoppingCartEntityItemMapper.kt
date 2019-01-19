package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.presentation.entities.ShoppingCartItem
import javax.inject.Inject


class ShoppingCartEntityItemMapper @Inject constructor() : Mapper<ShoppingCartItemEntity, ShoppingCartItem>() {
    override fun mapFrom(from: ShoppingCartItemEntity): ShoppingCartItem {
        return ShoppingCartItem(
                barcodeId = from.barcodeId,
                name = from.name,
                quantity = from.quantity,
                unit = from.unit,
                unitPrice = from.unitPrice,
                type = from.type,
                keywords = from.keywords
        )
    }

}