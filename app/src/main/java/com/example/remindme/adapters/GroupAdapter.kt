package com.example.remindme.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.remindme.R
import com.example.remindme.models.Group
import inflate
import kotlinx.android.synthetic.main.group_list_row.view.*

class GroupAdapter(private val groups: ArrayList<Group>) :
    RecyclerView.Adapter<GroupHolder>(){

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.nameText.text = groups.get(position).toString()
        holder.creationDate.text = "Creation date: " + groups.get(position).getDate()
        holder.tasksNumber.text = "Tasks number: " + groups.get(position).getTasksSize()
        holder.removeButton.setOnClickListener({ groups.removeAt(position) ; this.notifyDataSetChanged() })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val inflatedView = parent.inflate(R.layout.group_list_row, false)
        return GroupHolder(inflatedView)
    }

    override fun getItemCount(): Int {
       return groups.size
    }

}

class GroupHolder(v: View)
    : RecyclerView.ViewHolder(v), View.OnClickListener {

    val nameText = v.group_name
    val removeButton = v.group_remove
    val creationDate = v.creation_date
    val tasksNumber = v.tasks_number

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.group_info_bar.visibility == View.GONE){
            v.group_info_bar.visibility = View.VISIBLE
        }
        else{
            v.group_info_bar.visibility = View.GONE
        }
    }

}
