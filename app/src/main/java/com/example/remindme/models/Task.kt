package com.example.remindme.models

import java.util.*
import java.io.Serializable
import java.text.SimpleDateFormat

class Task(name: String, date: Date, group: Group): Serializable{

    private var name: String
    private var endDate: Date
    private var creationDate = Calendar.getInstance().getTime()
    private var done = false
    private var group: Group

    init{
        this.endDate = date
        this.group = group
        this.name = name
    }

    fun setTaskDone(){
        this.done = true
    }

    fun getName() : String{
        return this.name
    }

    fun setName(name: String){
        this.name = name
    }

    fun getDate() : String{
        val dateFormat = SimpleDateFormat("EEE, MMM d, ''yy")
        val strDate = dateFormat.format(endDate)

        return strDate
    }
}