package com.thetestcompany.domain.entities


data class ReceiptItemEntity(
        var id: Long,
        var receiptId: Long,
        var name: String,
        var quantity: Double,
        var unit: UnitsOfQuantity,
        var unitPrice: Double
)