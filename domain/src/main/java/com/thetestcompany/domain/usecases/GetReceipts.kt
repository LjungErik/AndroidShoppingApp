package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ReceiptRepository
import com.thetestcompany.domain.entities.ReceiptEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class GetReceipts(transformer: Transformer<List<ReceiptEntity>>,
                  private val receiptRespository: ReceiptRepository) : UseCase<List<ReceiptEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ReceiptEntity>> {
        return receiptRespository.getReceipts()
    }

}