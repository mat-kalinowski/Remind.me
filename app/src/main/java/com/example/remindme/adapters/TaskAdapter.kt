package com.example.remindme.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.remindme.R
import com.example.remindme.models.Header
import com.example.remindme.models.ListElement
import com.example.remindme.models.Task
import inflate
import kotlinx.android.synthetic.main.task_list_row.view.*
import kotlinx.android.synthetic.main.task_row.view.*



class TaskAdapter(private val elements : ArrayList<ListElement>, private var data : ArrayList<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)

        if(type == ListElement.TASK){
            var taskHolder = holder as TaskHolder
            var task = elements.get(position) as Task

            taskHolder.name.setText(task.getName())
            taskHolder.moreInfo.setText(task.getGroup())
        }
        else {
            var headerHolder = holder as HeaderHolder
            var header = elements.get(position) as Header

            headerHolder.nameText.setText(header.getName())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var toReturn: RecyclerView.ViewHolder

        if(viewType == ListElement.TASK){
            val inflatedView = parent.inflate(R.layout.task_row, false)
            toReturn = TaskHolder(inflatedView)
        }
        else{
            val inflatedView = parent.inflate(R.layout.task_list_row, false)
            toReturn = HeaderHolder(inflatedView)
        }

        return toReturn
    }

    fun deleteItem(position: Int) {
        var task = elements.get(position)
        elements.removeAt(position)
        data.remove(task)
        notifyItemRemoved(position)
    }

    fun deleteTaskDown(position: Int){
        var i = position
        elements.removeAt(position)
        notifyItemRemoved(position)

        while(i < elements.size && elements[i].getType() != ListElement.HEADER){
            deleteItem(i)
        }
    }

    override fun getItemCount() = elements.size

    override fun getItemViewType(position: Int): Int {
        return elements.get(position).getType()
    }

    class HeaderHolder(v: View): RecyclerView.ViewHolder(v){

        val nameText = v.group_title
        val mainFrame = v.main_layout
    }

    class TaskHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name = v.task_title
        var moreInfo = v.additional_info

    }
}


