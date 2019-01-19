package com.thetestcompany.domain

import com.thetestcompany.domain.entities.ReceiptItemEntity
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import io.reactivex.Observable


interface ReceiptDetailsRepository {
    fun getReceiptItems(receiptId: Long): Observable<List<ReceiptItemEntity>>

    fun insertReceiptItems(receiptItems: List<ReceiptItemEntity>) : Observable<List<Long>>
    fun insertCartItems(receiptId: Long, cartItems: List<ShoppingCartItemEntity>) : Observable<List<Long>>
}