package com.thetestcompany.data.entities

import com.thetestcompany.domain.entities.UnitsOfQuantity


data class ShoppingCartItemData(
        var barcodeId: String,
        var name: String,
        var quantity: Double,
        var unit: UnitsOfQuantity,
        var unitPrice: Double,
        var type: String,
        var keywords: Array<String>
)