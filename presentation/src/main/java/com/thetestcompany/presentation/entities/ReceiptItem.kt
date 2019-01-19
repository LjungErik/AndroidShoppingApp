package com.thetestcompany.presentation.entities

import com.thetestcompany.domain.entities.UnitsOfQuantity

data class ReceiptItem(
        var id: Long,
        var receiptId: Long,
        var name: String,
        var quantity: Double,
        var unit: UnitsOfQuantity,
        var unitPrice: Double
) {
    val totalPrice: Double
        get() {
            return quantity * unitPrice
        }
}