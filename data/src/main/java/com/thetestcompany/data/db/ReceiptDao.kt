package com.thetestcompany.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.thetestcompany.data.entities.ReceiptData


@Dao
interface ReceiptDao {

    @Query("SELECT * FROM Receipt")
    fun getReceipts(): List<ReceiptData>

    @Insert
    fun insertReceipt(receipt: ReceiptData): Long
}