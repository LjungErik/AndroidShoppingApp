package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.presentation.entities.ShoppingCartItem
import javax.inject.Inject


class ShoppingCartItemEntityMapper @Inject constructor(): Mapper<ShoppingCartItem, ShoppingCartItemEntity>() {
    override fun mapFrom(from: ShoppingCartItem): ShoppingCartItemEntity {
        return ShoppingCartItemEntity(
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