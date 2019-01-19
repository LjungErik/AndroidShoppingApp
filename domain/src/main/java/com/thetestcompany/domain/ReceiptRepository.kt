package com.thetestcompany.domain

import com.thetestcompany.domain.entities.ReceiptEntity
import io.reactivex.Observable


interface ReceiptRepository {
    fun getReceipts(): Observable<List<ReceiptEntity>>

    fun insertReceipt(receipt: ReceiptEntity): Observable<Long>
}