package com.example.remindme

import android.app.Application
import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.util.Log
import com.example.remindme.app_database.AppDatabase
import java.lang.Exception

class MainApplication : Application() {

    private lateinit var dbConn : AppDatabase

    override fun onCreate() {
        super.onCreate()

        AsyncTask.execute {

            try {
                dbConn = Room.databaseBuilder(
                    this,
                    AppDatabase::class.java,
                    "remindme.db"
                ).build()
            } catch (e: Exception) {
                Log.e("DB ERROR: ", " cannot connect to database")
                Log.e("DB ERROR: ", e.message)
            }

        }
    }

    fun getDb() : AppDatabase{
        return this.dbConn
    }
}

