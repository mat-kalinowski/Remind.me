package com.example.remindme.app_database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.remindme.models.ListElement
import com.example.remindme.models.ListElement.Companion.TASK
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "groups")
data class Group(
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id : Long = 0
)