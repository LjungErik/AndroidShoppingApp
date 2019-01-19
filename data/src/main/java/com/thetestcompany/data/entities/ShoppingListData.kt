package com.thetestcompany.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "ShoppingList")
data class ShoppingListData(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "createdAt")
        var createdAt: Date,

        @ColumnInfo(name = "color")
        var color: Int
)