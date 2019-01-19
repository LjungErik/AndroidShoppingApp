package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptItemEntity
import com.thetestcompany.presentation.entities.ReceiptItem
import javax.inject.Inject


class ReceiptItemEntityToItemMapper @Inject constructor(): Mapper<ReceiptItemEntity, ReceiptItem>() {

    override fun mapFrom(from: ReceiptItemEntity): ReceiptItem {
        return ReceiptItem(
                id = from.id,
                receiptId = from.receiptId,
                name = from.name,
                quantity = from.quantity,
                unit = from.unit,
                unitPrice = from.unitPrice
        )
    }

}