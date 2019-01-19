package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptEntity
import com.thetestcompany.presentation.entities.Receipt
import javax.inject.Inject


class ReceiptToReceiptEntityMapper @Inject constructor(): Mapper<Receipt, ReceiptEntity>() {
    override fun mapFrom(from: Receipt): ReceiptEntity {
        return ReceiptEntity(
                id = from.id,
                name = from.name,
                location = from.location
        )
    }
}