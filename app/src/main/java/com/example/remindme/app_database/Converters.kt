package com.example.remindme.app_database

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converters{
    @TypeConverter
    fun fromDate(value: Long?): Date?{
        return value?.let {Date(it)}
    }

    @TypeConverter
    fun toDate(date: Date?) : Long?{
        return date?.time?.toLong()

    }
}