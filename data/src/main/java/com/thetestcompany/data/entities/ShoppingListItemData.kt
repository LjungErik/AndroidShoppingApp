package com.thetestcompany.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.thetestcompany.domain.entities.UnitsOfQuantity


@Entity(tableName = "ShoppingListItem")
data class  ShoppingListItemData(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0,

        @ColumnInfo(name = "listName")
        var listName: String,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "quantity")
        var quantity: Double,

        @ColumnInfo(name = "unit")
        var unit: UnitsOfQuantity
)