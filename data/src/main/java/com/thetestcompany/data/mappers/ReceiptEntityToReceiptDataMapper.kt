package com.thetestcompany.data.mappers

import com.thetestcompany.data.entities.ReceiptData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptEntity
import javax.inject.Inject


class ReceiptEntityToReceiptDataMapper @Inject constructor(): Mapper<ReceiptEntity, ReceiptData>() {

    override fun mapFrom(from: ReceiptEntity): ReceiptData {
        return ReceiptData(
                id = from.id,
                name = from.name,
                location = from.location
        )
    }

}