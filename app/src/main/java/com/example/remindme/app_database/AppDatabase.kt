package com.example.remindme.app_database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database( entities = [(Task::class),(Group::class)], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDAO
    abstract fun groupDao(): GroupDAO
}