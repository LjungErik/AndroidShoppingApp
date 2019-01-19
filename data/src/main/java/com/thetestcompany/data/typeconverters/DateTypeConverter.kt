package com.thetestcompany.data.typeconverters

import android.arch.persistence.room.TypeConverter
import java.util.*


class DateTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        if(value == null)
            return null
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(value: Date?): Long? {
        if(value == null)
            return null
        return value.time
    }

}