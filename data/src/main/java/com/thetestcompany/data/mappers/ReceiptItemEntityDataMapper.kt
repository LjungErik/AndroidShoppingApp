package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ReceiptItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptItemEntity
import javax.inject.Inject


class ReceiptItemEntityDataMapper @Inject constructor(): Mapper<ReceiptItemEntity, ReceiptItemData>() {

    override fun mapFrom(from: ReceiptItemEntity): ReceiptItemData {
        return ReceiptItemData(
                id = from.id,
                receiptId = from.receiptId,
                name = from.name,
                quantity = from.quantity,
                unit = from.unit,
                unitPrice = from.unitPrice
        )
    }

}