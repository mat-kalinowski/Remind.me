package com.example.remindme.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.remindme.R
import com.example.remindme.models.Task
import inflate
import kotlinx.android.synthetic.main.group_list_row.view.*

class GroupAdapter(private val groups: ArrayList<String>, private val tasks: ArrayList<Task>) :
    RecyclerView.Adapter<GroupHolder>(){

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.nameText.text = groups.get(position)
        holder.removeButton.setOnClickListener{
            val delGroup = groups.get(position)

            for (task in tasks){
                Log.e("hui", "jadom")
                if(task.getGroup().compareTo(delGroup) == 0){
                    tasks.remove(task)
                }
            }

            groups.removeAt(position)
            this.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val inflatedView = parent.inflate(R.layout.group_list_row, false)
        return GroupHolder(inflatedView)
    }

    override fun getItemCount(): Int {
       return groups.size
    }
}

class GroupHolder(v: View) : RecyclerView.ViewHolder(v) {
    val nameText = v.group_title
    val removeButton = v.group_remove
}
