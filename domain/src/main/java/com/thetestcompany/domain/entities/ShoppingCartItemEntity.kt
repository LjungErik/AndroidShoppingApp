package com.thetestcompany.domain.entities


data class ShoppingCartItemEntity(
        var barcodeId: String,
        var name: String,
        var quantity: Double,
        var unit: UnitsOfQuantity,
        var unitPrice: Double,
        var type: String,
        var keywords: Array<String>
)