package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ShoppingCartItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import javax.inject.Inject


class ShoppingCartDataEntityMapper @Inject constructor(): Mapper<ShoppingCartItemData, ShoppingCartItemEntity>() {
    override fun mapFrom(from: ShoppingCartItemData): ShoppingCartItemEntity {
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