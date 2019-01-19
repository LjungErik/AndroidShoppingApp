package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ReceiptDetailsRepository
import com.thetestcompany.domain.entities.ReceiptItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class GetReceiptItems(transformer: Transformer<List<ReceiptItemEntity>>,
                      private val receiptDetailsRepository: ReceiptDetailsRepository) : UseCase<List<ReceiptItemEntity>>(transformer) {

    companion object {
        private val PARAM_RECEIPT_ID = "param:receiptId"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ReceiptItemEntity>> {
        val id = data?.get(PARAM_RECEIPT_ID)

        return id?.let {
            val receiptId = it as Long
            return receiptDetailsRepository.getReceiptItems(receiptId)
        } ?: return Observable.error({IllegalArgumentException("ReceiptId must be provided")})
    }

    fun get(receiptId: Long): Observable<List<ReceiptItemEntity>> {
        val data = HashMap<String, Long>()
        data[PARAM_RECEIPT_ID] = receiptId
        return observable(data)
    }

}