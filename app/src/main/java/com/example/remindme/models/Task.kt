package com.example.remindme.models

import com.example.remindme.models.ListElement.Companion.TASK
import java.util.*
import java.io.Serializable
import java.text.SimpleDateFormat

class Task(name: String, date: Date, group: String): Serializable, ListElement{

    private var endDate: Date
    private var name: String
    private var done = false
    private var group: String

    init{
        this.endDate = date
        this.group = group
        this.name = name
    }

    override fun getType(): Int {
        return TASK
    }

    fun setName(name: String){
        this.name = name
    }

    fun getDate() : String{
        val dateFormat = SimpleDateFormat("EEE, MMM d, ''yy")
        val strDate = dateFormat.format(endDate)

        return strDate
    }

    fun getRawDate() : Date{
        return this.endDate
    }

    fun getName() : String{
        return this.name
    }

    fun getGroup() : String{
        return this.group
    }
}