package com.thetestcompany.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "Receipt")
data class ReceiptData(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "location")
        var location: String
)