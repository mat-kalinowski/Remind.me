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
import java.util.*


class TaskAdapter( private var data : ArrayList<Task>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val elements : ArrayList<ListElement> = ArrayList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)

        if(type == ListElement.TASK){
            val taskHolder = holder as TaskHolder
            val task = elements.get(position) as Task

            taskHolder.name.setText(task.getName())
            taskHolder.moreInfo.setText(task.getGroup())
        }
        else {
            val headerHolder = holder as HeaderHolder
            val header = elements.get(position) as Header

            headerHolder.nameText.setText(header.getName())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val toReturn: RecyclerView.ViewHolder

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

    override fun getItemCount() = elements.size

    override fun getItemViewType(position: Int): Int {
        return elements.get(position).getType()
    }

    fun filterByText(text: String){
        val matchText = text.toLowerCase()
        elements.clear()
        elements.add(Header("Search results"))

        for(i in 0.. (data.size-1)){
            if(data[i].getName().toLowerCase().contains(matchText)){
                elements.add(data[i])
            }
        }
        this.notifyDataSetChanged()
    }

    fun filterByGroup(){
        Collections.sort(data){ o1, o2 -> o1.getGroup().compareTo(o2.getGroup()) }
        elements.clear()

        if(data.size > 0){
            elements.add(Header(data[0].getGroup()))
            elements.add(data[0])
        }

        for(i in 1 .. (data.size-1)){
            if(data[i-1].getGroup() != data[i].getGroup()){
                elements.add(Header(data[i].getGroup()))
            }
            elements.add(data[i])
        }
        this.notifyDataSetChanged()
    }

    fun filterByDate(){

        Collections.sort(data){ o1, o2 -> o1.getRawDate().compareTo(o2.getRawDate()) }
        elements.clear()

        if(data.size > 0){
            elements.add(Header(data[0].getDate()))
            elements.add(data[0])
        }

        for(i in 1 .. (data.size-1)){
            if(data[i-1].getRawDate() != data[i].getRawDate()){
                elements.add(Header(data[i].getDate()))
            }
            elements.add(data[i])
        }
        this.notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val task = elements.get(position)
        elements.removeAt(position)
        data.remove(task)
        notifyItemRemoved(position)
    }

    fun deleteTaskDown(position: Int){
        val i = position
        elements.removeAt(position)
        notifyItemRemoved(position)

        while(i < elements.size && elements[i].getType() != ListElement.HEADER){
            deleteItem(i)
        }
    }

    class HeaderHolder(v: View): RecyclerView.ViewHolder(v){
        var nameText = v.group_title
    }

    class TaskHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name = v.task_title
        var moreInfo = v.additional_info
    }
}


