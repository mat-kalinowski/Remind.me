package com.example.remindme.models

import android.util.Log
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Group(): Serializable{
    private lateinit var name: String
    private lateinit var date: Date
    private var tasks: ArrayList<Task> = ArrayList()

    constructor(name: String) : this() {
        this.name = name
        this.date = Calendar.getInstance().getTime();
    }

    constructor(date: Date) : this() {
        val dateFormat = SimpleDateFormat("EEE, MMM d, yy")
        this.name = dateFormat.format(date)
        this.date = date
    }

    override fun toString() : String{
        return this.name
    }

    fun addTask(newTask: Task){
        this.tasks.add(newTask)
    }

    fun getTask(i : Int) : Task{
        return this.tasks[i]
    }

    fun removeTask(delTask: Task){
        Log.e("remove",delTask.getName())

        this.tasks.remove(delTask)
    }

    fun getTasksSize() : Int{
        return this.tasks.size
    }
    fun getDate(): Date{
        return this.date
    }
}