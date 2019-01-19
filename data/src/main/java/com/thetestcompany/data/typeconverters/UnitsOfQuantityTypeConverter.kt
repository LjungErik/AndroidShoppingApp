package com.thetestcompany.data.typeconverters

import android.arch.persistence.room.TypeConverter
import com.thetestcompany.domain.entities.UnitsOfQuantity


class UnitsOfQuantityTypeConverter {

    @TypeConverter
    fun toUnitsOfQuantity(value: Int?) : UnitsOfQuantity? {
        if(value == null)
            return null
        return UnitsOfQuantity.fromInt(value)
    }

    @TypeConverter
    fun toInt(value: UnitsOfQuantity?) : Int? {
        if(value == null)
            return null
        return value.id
    }
}