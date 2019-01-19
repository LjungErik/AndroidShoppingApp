package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ReceiptData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptEntity
import javax.inject.Inject


class ReceiptDataToReceiptEntityMapper @Inject constructor(): Mapper<ReceiptData, ReceiptEntity>() {

    override fun mapFrom(from: ReceiptData): ReceiptEntity {
        return ReceiptEntity(
                id = from.id,
                name = from.name,
                location = from.location
        )
    }

}