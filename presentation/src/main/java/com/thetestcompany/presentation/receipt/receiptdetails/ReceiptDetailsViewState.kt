package com.thetestcompany.presentation.receipt.receiptdetails

import com.thetestcompany.presentation.entities.ReceiptItem


data class ReceiptDetailsViewState(
        var total: Double = 0.0,
        var items: List<ReceiptItem> = listOf()
)