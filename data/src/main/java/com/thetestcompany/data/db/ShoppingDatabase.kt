package com.thetestcompany.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.thetestcompany.data.entities.ReceiptData
import com.thetestcompany.data.entities.ReceiptItemData
import com.thetestcompany.data.entities.ShoppingListData
import com.thetestcompany.data.entities.ShoppingListItemData
import com.thetestcompany.data.typeconverters.DateTypeConverter
import com.thetestcompany.data.typeconverters.UnitsOfQuantityTypeConverter


@Database(entities = [ShoppingListData::class, ShoppingListItemData::class, ReceiptData::class, ReceiptItemData::class], version = 1)
@TypeConverters(DateTypeConverter::class, UnitsOfQuantityTypeConverter::class)
abstract class ShoppingDatabase: RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

    abstract fun shoppingListItemDao(): ShoppingListItemDao

    abstract fun receiptDao(): ReceiptDao

    abstract fun receiptItemDao(): ReceiptItemDao
}