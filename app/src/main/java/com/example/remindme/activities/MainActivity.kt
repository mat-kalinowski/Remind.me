package com.example.remindme.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.example.remindme.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun editGroupsActivity(v: View){
        val k = Intent(this@MainActivity, EditGroupActivity::class.java)
        startActivity(k)
    }

    fun viewTasksActivity(v: View){
        val k = Intent(this@MainActivity, ViewTasksActivity::class.java)
        startActivity(k)
    }

    fun aboutActivity(v: View){
        //val k = Intent(this@MainActivity, AboutActivity::class.java)
        //startActivity(k)
    }

    fun authorActivity(v: View){
        //val k = Intent(this@MainActivity, AuthorActivity::class.java)
        //startActivity(k)
    }
}
