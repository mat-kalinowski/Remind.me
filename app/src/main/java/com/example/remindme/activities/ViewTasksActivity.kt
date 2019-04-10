package com.example.remindme.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.remindme.models.Group
import com.example.remindme.models.Task
import com.example.remindme.utils.*
import kotlinx.android.synthetic.main.activity_view_tasks.*
import java.io.IOException
import java.util.*
import android.widget.ArrayAdapter
import com.example.remindme.R
import com.example.remindme.adapters.TaskDateAdapter


class ViewTasksActivity : AppCompatActivity() {

    private lateinit var taskList: ArrayList<Task>
    private lateinit var groupList: ArrayList<Group>
    private lateinit var dateList: ArrayList<Group>

    private lateinit var taskDate: Date
    private lateinit var taskGroup: Group

    private lateinit var groupAdapter: TaskDateAdapter
    private lateinit var dateAdapter: TaskDateAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tasks)

        try {
            groupList = readObject(this,"GROUP_LIST") as ArrayList<Group>
        }
        catch(ex: IOException){
            groupList = ArrayList()
        }

        try {
            taskList = readObject(this, "TASKS_LIST") as ArrayList<Task>
        }
        catch(ex: IOException){
            taskList = ArrayList()
        }

        try{
            dateList = readObject(this, "DATE_LIST") as ArrayList<Group>
        }
        catch(ex: IOException){
            dateList = ArrayList()
        }

        layoutManager = LinearLayoutManager(this)
        task_recycler.layoutManager = layoutManager

        dateAdapter = TaskDateAdapter(dateList,this)
        groupAdapter = TaskDateAdapter(groupList,this)
        task_recycler.adapter = dateAdapter

    }

    override fun onDestroy(){
        super.onDestroy()

        writeObject(this, "TASKS_LIST", taskList)
        writeObject(this, "GROUP_LIST", groupList)
        writeObject(this,"DATE_LIST", dateList)
    }

    fun addActivity(v: View){
        val taskName = task_name.text.toString()
        val newTask = Task(taskName, taskDate, taskGroup)

        taskList.add(newTask)

        var dateGroup = dateList.stream().filter({ group -> group.getDate().equals(taskDate) }).findFirst().orElse(null)

        if(dateGroup == null){
            dateGroup = Group(taskDate)
            dateList.add(dateGroup)
            dateAdapter.addGroup(dateGroup)
        }

        dateAdapter.addTask(dateGroup,newTask)
        groupAdapter.addTask(taskGroup, newTask)

    }

    fun selectGroup(v: View){
        val dialogBuilder = AlertDialog.Builder(this@ViewTasksActivity)
        val groupAdapter = ArrayAdapter<Group>(this, R.layout.dialog_item, groupList)

        dialogBuilder.setTitle("Choose task group")
        dialogBuilder.setAdapter(groupAdapter, { dialogInterface: DialogInterface, i: Int ->
            taskGroup = groupList[i]
            group_name.setText(taskGroup.toString())
        })

        val alert = dialogBuilder.create()
        alert.show()
    }

    fun selectDate(v: View){
        val c = Calendar.getInstance()

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            displayDate.text = "" + day + "/" + month + "/" + year
            taskDate = Date(year,month,day)

        }, c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    fun backToMenu(v: View){
        val mainMenu = Intent(this@ViewTasksActivity, MainActivity::class.java)
        startActivity(mainMenu)
        finish()
    }

    fun showDisplayBar(v: View){
        if(bar_holder.displayedChild == 2 && bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 2
        }
    }

    fun showSearchBar(v: View){
        if(bar_holder.displayedChild == 1 && bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 1
        }
    }
    fun showTaskBar(v: View){
        if(bar_holder.displayedChild == 0 && bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 0
        }
    }

    fun changeFilter(v: View){
        if(change_display.isChecked){
            change_display.setText("Group")
            task_recycler.adapter = groupAdapter
        }
        else{
            change_display.setText("Date")
            task_recycler.adapter = dateAdapter
        }
    }
}