package com.example.remindme.adapters

import android.app.Activity
import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.remindme.R
import com.example.remindme.app_database.AppDatabase
import com.example.remindme.app_database.Group
import com.example.remindme.app_database.Task
import inflate
import kotlinx.android.synthetic.main.group_list_row.view.*
import java.lang.Exception

class GroupAdapter(activity: Activity) : RecyclerView.Adapter<GroupHolder>(){

    private var groups: ArrayList<Group> = ArrayList()
    private var tasks: ArrayList<Task> = ArrayList()
    private lateinit var dbHandler: AppDatabase

    init{
        Log.e("LOADING", " FROM DB")
        AsyncTask.execute {

            try {
                dbHandler = Room.databaseBuilder(
                    activity,
                    AppDatabase::class.java,
                    "remindme.db"
                ).build()
            } catch (e: Exception) {
                Log.e("DB ERROR: ", " cannot connect to database")
                Log.e("DB ERROR: ", e.message)
            }

            tasks = dbHandler.taskDao().getAll() as java.util.ArrayList<Task>
            groups = dbHandler.groupDao().getAll() as ArrayList<Group>
            activity.runOnUiThread{notifyDataSetChanged()}
        }

    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.nameText.text = groups[position].name
        holder.removeButton.setOnClickListener{
            val delGroup = groups[position]

            for (i  in (tasks.size-1) downTo 0){
                if(tasks[i].group.compareTo(delGroup.name) == 0){
                    val toDel = tasks[i]
                    tasks.remove(toDel)
                    AsyncTask.execute{dbHandler.taskDao().delete(toDel)}
                }
            }

            val removedGroup = groups[position]
            AsyncTask.execute{dbHandler.groupDao().delete(removedGroup)}
            groups.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val inflatedView = parent.inflate(R.layout.group_list_row, false)
        return GroupHolder(inflatedView)
    }

    fun addItem(group: Group){
        groups.add(group)
        notifyDataSetChanged()

        AsyncTask.execute{
            group.id = dbHandler.groupDao().insert(group)
        }
    }

    override fun getItemCount(): Int {
       return groups.size
    }
}

class GroupHolder(v: View) : RecyclerView.ViewHolder(v) {
    val nameText = v.group_title
    val removeButton = v.group_remove
}
