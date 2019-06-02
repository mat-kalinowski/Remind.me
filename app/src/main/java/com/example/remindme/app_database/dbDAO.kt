package com.example.remindme.app_database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TaskDAO {

    @Query("select * from tasks")
    fun getAll() : List<Task>

    @Insert
    fun insert(task : Task) : Long

    @Delete
    fun delete(vararg task:Task)
}

@Dao
interface GroupDAO {
    @Query("select * from groups")
    fun getAll() : List<Group>

    @Insert
    fun insert(group:Group) : Long

    @Delete
    fun delete(vararg group:Group)
}