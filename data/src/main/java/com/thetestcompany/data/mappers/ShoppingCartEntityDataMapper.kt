package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ShoppingCartItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import javax.inject.Inject


class ShoppingCartEntityDataMapper @Inject constructor(): Mapper<ShoppingCartItemEntity, ShoppingCartItemData>() {
    override fun mapFrom(from: ShoppingCartItemEntity): ShoppingCartItemData {
        return ShoppingCartItemData(
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