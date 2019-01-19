package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptItemEntity
import com.thetestcompany.presentation.entities.ReceiptItem
import javax.inject.Inject


class ReceiptItemToEntityMapper @Inject constructor(): Mapper<ReceiptItem, ReceiptItemEntity>() {

    override fun mapFrom(from: ReceiptItem): ReceiptItemEntity {
        return ReceiptItemEntity(
                id = from.id,
                receiptId = from.receiptId,
                name = from.name,
                quantity = from.quantity,
                unit = from.unit,
                unitPrice = from.unitPrice
        )
    }

}