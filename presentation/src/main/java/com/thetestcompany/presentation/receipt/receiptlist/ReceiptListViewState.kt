package com.thetestcompany.presentation.receipt.receiptlist

import com.thetestcompany.presentation.entities.Receipt


data class ReceiptListViewState(
        var receipts: List<Receipt> = listOf()
)