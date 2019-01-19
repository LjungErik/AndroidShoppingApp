package com.thetestcompany.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.thetestcompany.data.entities.ReceiptItemData


@Dao
interface ReceiptItemDao {

    @Query("SELECT * FROM ReceiptItem WHERE receiptId = :receipt")
    fun getItems(receipt: Long) : List<ReceiptItemData>

    @Insert
    fun insertReceiptItems(receiptItems: List<ReceiptItemData>): List<Long>
}