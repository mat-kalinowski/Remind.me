package com.example.remindme.models

interface ListElement{
    companion object {
        val TASK = 1
        val HEADER = 0
    }

    fun getType() : Int
}