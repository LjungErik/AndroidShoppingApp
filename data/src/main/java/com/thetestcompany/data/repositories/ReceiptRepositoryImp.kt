package com.thetestcompany.data.repositories

import com.thetestcompany.data.db.ReceiptDao
import com.thetestcompany.data.entities.ReceiptData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ReceiptRepository
import com.thetestcompany.domain.entities.ReceiptEntity
import io.reactivex.Observable


class ReceiptRepositoryImp(private val receiptDao: ReceiptDao,
                           private val dataMapper: Mapper<ReceiptData, ReceiptEntity>,
                           private val entityMapper: Mapper<ReceiptEntity, ReceiptData>): ReceiptRepository {

    override fun getReceipts(): Observable<List<ReceiptEntity>> {
        return Observable.fromCallable {
            receiptDao.getReceipts().map{ dataMapper.mapFrom(it) }
        }
    }

    override fun insertReceipt(receipt: ReceiptEntity): Observable<Long> {
        return Observable.fromCallable {
            receiptDao.insertReceipt(entityMapper.mapFrom(receipt))
        }
    }
}