package com.thetestcompany.presentation.mappers

import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptEntity
import com.thetestcompany.presentation.entities.Receipt
import javax.inject.Inject


class ReceiptEntityToReceiptMapper @Inject constructor(): Mapper<ReceiptEntity, Receipt>() {

    override fun mapFrom(from: ReceiptEntity): Receipt {
        return Receipt(
                id = from.id,
                name =  from.name,
                location = from.location
        )
    }

}