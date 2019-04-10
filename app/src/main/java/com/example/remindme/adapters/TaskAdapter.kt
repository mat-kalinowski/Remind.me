package com.example.remindme.adapters

import android.content.Context
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import com.example.remindme.R
import com.example.remindme.models.Group
import com.example.remindme.models.Task
import inflate
import kotlinx.android.synthetic.main.task_list_row.view.*

class TaskDateAdapter(private val tasksContainers: List<Group>, private val appContext: Context) :
    RecyclerView.Adapter<TaskContainerHolder>(){

    private val groupSortedList: SortedList<Group>
    private var context: Context


    init {
        this.context = appContext
        groupSortedList = SortedList(Group::class.java, object : SortedListAdapterCallback<Group>(this) {
            override fun compare(o1: Group, o2: Group): Int = o1.getDate().compareTo(o2.getDate())

            override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean = oldItem.toString().compareTo(newItem.toString()) == 0

            override fun areItemsTheSame(item1: Group, item2: Group): Boolean = item1 == item2
        })

        addGroups(tasksContainers)
    }

    override fun onBindViewHolder(holder: TaskContainerHolder, position: Int) {
        val currGroup = groupSortedList[position]
        holder.nameText.text = currGroup.toString()

        for( i in 0 .. (currGroup.getTasksSize()-1) ){
            var row = TableRow(context)
            var text = TextView(context)
            var currTask = currGroup.getTask(i)
            //var add_text = TextView(context)

            row.setBackgroundResource(R.drawable.task_field)
            row.setPadding(0,20,0,20)

            //add_text.setText(currGroup.getTask(i).getDate())
            text.setText(currTask.getName())
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.0f)

            row.addView(text)
            //row.addView(add_text)

            row.setOnClickListener( {
                    currGroup.removeTask(currTask)
                    this.notifyItemChanged(i)}
            )

            holder.groupTasks.addView(row)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskContainerHolder {
        val inflatedView = parent.inflate(R.layout.task_list_row, false)
        return TaskContainerHolder(inflatedView)
    }

    override fun getItemCount() = groupSortedList.size()

    fun addGroup(groups: Group) {
       groupSortedList.add(groups)
    }

    fun addGroups(groups: List<Group>) {
        groupSortedList.addAll(groups)
    }

    fun removeGroup(index: Int) {
        if (groupSortedList.size() == 0) {
            return
        }
        groupSortedList.remove(groupSortedList.get(index))
    }

    fun addTask(group: Group,task: Task){
        var index = groupSortedList.indexOf(group)
        group.addTask(task)

        this.notifyItemChanged(index)
    }
}

class TaskContainerHolder(v: View):
    RecyclerView.ViewHolder(v), View.OnClickListener {

    val nameText = v.group_name
    val groupTasks = v.group_tasks

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.group_tasks.visibility == View.GONE){
            v.group_tasks.visibility = View.VISIBLE
        }
        else{
            v.group_tasks.visibility = View.GONE
        }
    }
}
