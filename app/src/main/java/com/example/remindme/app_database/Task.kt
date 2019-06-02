package com.example.remindme.app_database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.remindme.models.ListElement
import com.example.remindme.models.ListElement.Companion.TASK
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "date") var endDate: Date,
    @ColumnInfo(name = "group") var group: String,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id : Long = 0
) : ListElement {

    override fun getType(): Int {
        return TASK
    }

    fun getDate() : String{
        val dateFormat = SimpleDateFormat("EEE, MMM d, ''yy")
        val strDate = dateFormat.format(endDate)

        return strDate
    }

    fun getRawDate() : Date{
        return this.endDate
    }
}