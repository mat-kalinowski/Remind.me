package com.example.remindme.models

import com.example.remindme.models.ListElement.Companion.HEADER

class Header(name: String) : ListElement {

    private lateinit var name : String

    init{
        this.name = name
    }

    override fun getType(): Int {
        return HEADER
    }

    fun setName(name: String){
        this.name = name
    }

    fun getName() : String {
        return this.name
    }
}