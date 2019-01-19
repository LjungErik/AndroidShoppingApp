package com.thetestcompany.data.entities

import com.thetestcompany.domain.entities.UnitsOfQuantity


data class ProductData(
        var barcodeId: String,
        var name: String,
        var quantity: Double,
        var unitId: Int,
        var price: Double,
        var type: String,
        var keywords: Array<String>
)