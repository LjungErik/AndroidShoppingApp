package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ProductData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.entities.UnitsOfQuantity
import javax.inject.Inject


class ProductToShoppingCartEntityMapper @Inject constructor(): Mapper<ProductData, ShoppingCartItemEntity>() {
    override fun mapFrom(from: ProductData): ShoppingCartItemEntity {
        return ShoppingCartItemEntity(
                barcodeId = from.barcodeId,
                name = from.name,
                quantity = from.quantity,
                unit = UnitsOfQuantity.fromInt(from.unitId),
                unitPrice = from.price,
                type = from.type,
                keywords = from.keywords
        )
    }
}