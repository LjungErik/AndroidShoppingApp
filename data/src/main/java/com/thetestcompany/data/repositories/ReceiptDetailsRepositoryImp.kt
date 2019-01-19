package com.thetestcompany.data.repositories

import com.thetestcompany.data.db.ReceiptItemDao
import com.thetestcompany.data.entities.ReceiptItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ReceiptDetailsRepository
import com.thetestcompany.domain.entities.ReceiptItemEntity
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import io.reactivex.Observable


class ReceiptDetailsRepositoryImp(private val receiptItemDao: ReceiptItemDao,
                                  private val dataMapper: Mapper<ReceiptItemData, ReceiptItemEntity>,
                                  private val entityMapper: Mapper<ReceiptItemEntity, ReceiptItemData>): ReceiptDetailsRepository {

    override fun getReceiptItems(receiptId: Long): Observable<List<ReceiptItemEntity>> {
        return Observable.fromCallable {
            receiptItemDao.getItems(receiptId).map { dataMapper.mapFrom(it) }
        }
    }

    override fun insertReceiptItems(receiptItems: List<ReceiptItemEntity>): Observable<List<Long>> {
        return Observable.fromCallable {
            receiptItemDao.insertReceiptItems(receiptItems.map { entityMapper.mapFrom(it) })
        }
    }

    override fun insertCartItems(receiptId: Long, cartItems: List<ShoppingCartItemEntity>): Observable<List<Long>> {
        return Observable.fromCallable {
            receiptItemDao.insertReceiptItems(cartItems.map { item ->
                ReceiptItemData(
                        receiptId = receiptId,
                        name = item.name,
                        quantity = item.quantity,
                        unit = item.unit,
                        unitPrice = item.unitPrice
                )
            })
        }
    }
}