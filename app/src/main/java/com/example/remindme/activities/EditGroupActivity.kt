package com.example.remindme.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.remindme.models.Group
import com.example.remindme.adapters.GroupAdapter
import com.example.remindme.R
import com.example.remindme.utils.*

import kotlinx.android.synthetic.main.activity_edit_tasks.*
import java.io.IOException

class EditGroupActivity : AppCompatActivity() {


    private lateinit var groupList: ArrayList<Group>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tasks)

        try {
            groupList = readObject(this,"GROUP_LIST") as ArrayList<Group>
        }
        catch(ex: IOException){
            groupList = ArrayList()
        }

        layoutManager = LinearLayoutManager(this)
        group_recycler.layoutManager = layoutManager

        adapter = GroupAdapter(groupList)
        group_recycler.adapter = adapter

    }

    override fun onDestroy(){
        writeObject(this, "GROUP_LIST", groupList)

        super.onDestroy()

    }

    fun createNewGroup(v: View){
        
        if(bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 1
        }
    }

    fun addNewGroup(v: View){
        groupList.add(Group(new_group.text.toString()))
        adapter.notifyDataSetChanged()
    }

    fun searchGroup(v: View){
        if(bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 0
        }
    }

    fun backToMenu(v: View){
        val mainMenu = Intent(this@EditGroupActivity, MainActivity::class.java)
        startActivity(mainMenu)
        finish()
    }
}
