package com.quantumman.whooshservice.data.local.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(long: Long): Calendar? {
        val date = Calendar.getInstance()
        date.timeInMillis = long
        return date
    }

    @TypeConverter
    fun fromDate(date: Calendar?): Long? {
        return date?.time?.time
    }
}